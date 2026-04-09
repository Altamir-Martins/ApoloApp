package com.apolo.app.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.geojson.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MapViewModel : ViewModel() {
    
    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation.asStateFlow()
    
    private val _userHeading = MutableStateFlow(0f)
    val userHeading: StateFlow<Float> = _userHeading.asStateFlow()

    private val _destination = MutableStateFlow<SearchResult?>(null)
    val destination: StateFlow<SearchResult?> = _destination.asStateFlow()

    private val _isNavigating = MutableStateFlow(false)
    val isNavigating: StateFlow<Boolean> = _isNavigating.asStateFlow()

    private val _isRoutePreview = MutableStateFlow(false)
    val isRoutePreview: StateFlow<Boolean> = _isRoutePreview.asStateFlow()

    private val _routePoints = MutableStateFlow<List<Point>>(emptyList())
    val routePoints: StateFlow<List<Point>> = _routePoints.asStateFlow()

    private val _routeDistanceMeters = MutableStateFlow(0.0)
    val routeDistanceMeters: StateFlow<Double> = _routeDistanceMeters.asStateFlow()

    private val _routeDurationSeconds = MutableStateFlow(0.0)
    val routeDurationSeconds: StateFlow<Double> = _routeDurationSeconds.asStateFlow()

    private val _routeSafety = MutableStateFlow("Seguro")
    val routeSafety: StateFlow<String> = _routeSafety.asStateFlow()

    private val _reports = MutableStateFlow<List<Report>>(emptyList())
    val reports: StateFlow<List<Report>> = _reports.asStateFlow()
    
    private val _selectedReport = MutableStateFlow<Report?>(null)
    val selectedReport: StateFlow<Report?> = _selectedReport.asStateFlow()
    
    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> = _searchResults.asStateFlow()
    
    private val _riskFilter = MutableStateFlow<String?>(null)
    val riskFilter: StateFlow<String?> = _riskFilter.asStateFlow()

    private var mapboxAccessToken: String? = null

    init {
        // Carregar simulado de dados
        loadReports()
    }

    fun updateLocation(location: Location) {
        _currentLocation.value = location
        if (_destination.value != null && _routePoints.value.isEmpty()) {
            mapboxAccessToken?.let { token ->
                fetchRoute(location, Point.fromLngLat(_destination.value!!.longitude, _destination.value!!.latitude), token)
            }
        }
    }

    fun updateHeading(heading: Float) {
        _userHeading.value = heading
    }

    fun previewNavigationTo(destination: SearchResult, accessToken: String) {
        _destination.value = destination
        _isNavigating.value = false
        _isRoutePreview.value = true
        mapboxAccessToken = accessToken
        _routePoints.value = emptyList()
        _routeDistanceMeters.value = 0.0
        _routeDurationSeconds.value = 0.0
        _routeSafety.value = "Calculando..."

        _currentLocation.value?.let { location ->
            fetchRoute(location, Point.fromLngLat(destination.longitude, destination.latitude), accessToken)
        }
    }

    fun confirmNavigation() {
        if (_routePoints.value.isNotEmpty() && _destination.value != null) {
            _isNavigating.value = true
            _isRoutePreview.value = false
        }
    }

    fun cancelNavigationPreview() {
        _destination.value = null
        _routePoints.value = emptyList()
        _isNavigating.value = false
        _isRoutePreview.value = false
        _routeDistanceMeters.value = 0.0
        _routeDurationSeconds.value = 0.0
        _routeSafety.value = "Seguro"
    }

    fun clearNavigation() {
        _destination.value = null
        _routePoints.value = emptyList()
        _isNavigating.value = false
        _isRoutePreview.value = false
        _routeDistanceMeters.value = 0.0
        _routeDurationSeconds.value = 0.0
        _routeSafety.value = "Seguro"
    }

    private fun fetchRoute(origin: Location, destination: Point, accessToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val url = URL(
                "https://api.mapbox.com/directions/v5/mapbox/driving/${origin.longitude},${origin.latitude};${destination.longitude()},${destination.latitude()}?geometries=geojson&overview=full&access_token=$accessToken"
            )

            (url.openConnection() as HttpURLConnection).run {
                requestMethod = "GET"
                connectTimeout = 10000
                readTimeout = 10000
                try {
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStream.bufferedReader().use { reader ->
                            val json = JSONObject(reader.readText())
                            val routes = json.getJSONArray("routes")
                            if (routes.length() > 0) {
                                val route = routes.getJSONObject(0)
                                val geometry = route.getJSONObject("geometry")
                                val coordinates = geometry.getJSONArray("coordinates")
                                val points = mutableListOf<Point>()
                                for (i in 0 until coordinates.length()) {
                                    val coord = coordinates.getJSONArray(i)
                                    points.add(Point.fromLngLat(coord.getDouble(0), coord.getDouble(1)))
                                }

                                _routeDistanceMeters.value = route.optDouble("distance", 0.0)
                                _routeDurationSeconds.value = route.optDouble("duration", 0.0)
                                _routeSafety.value = calculateSafety(origin, destination)
                                _routePoints.value = points
                            } else {
                                _routePoints.value = emptyList()
                                _routeDistanceMeters.value = 0.0
                                _routeDurationSeconds.value = 0.0
                                _routeSafety.value = "Indisponível"
                            }
                        }
                    } else {
                        _routePoints.value = emptyList()
                        _routeDistanceMeters.value = 0.0
                        _routeDurationSeconds.value = 0.0
                        _routeSafety.value = "Indisponível"
                    }
                } catch (exception: Exception) {
                    _routePoints.value = emptyList()
                    _routeDistanceMeters.value = 0.0
                    _routeDurationSeconds.value = 0.0
                    _routeSafety.value = "Indisponível"
                } finally {
                    disconnect()
                }
            }
        }
    }

    private fun calculateSafety(origin: Location, destination: Point): String {
        val nearbyReports = _reports.value.filter { report ->
            val reportDistance = FloatArray(1)
            Location.distanceBetween(
                origin.latitude,
                origin.longitude,
                report.latitude,
                report.longitude,
                reportDistance
            )
            reportDistance[0] <= 1000 || Location.distanceBetween(
                destination.latitude(),
                destination.longitude(),
                report.latitude,
                report.longitude,
                reportDistance
            ).let { reportDistance[0] <= 1000 }
        }

        return when {
            nearbyReports.any { it.riskLevel.contains("Crítico", ignoreCase = true) || it.riskLevel.contains("Alto", ignoreCase = true) } -> "Atenção"
            nearbyReports.isNotEmpty() -> "Moderado"
            else -> "Seguro"
        }
    }

    fun loadReports() {
        // Simular carregamento de denúncias do backend
        _reports.value = listOf(
            Report(
                id = "report_1",
                type = "Roubo/Furto",
                description = "Assalto na avenida principal",
                latitude = -23.5608,
                longitude = -46.6560,
                riskLevel = "Alto",
                timestamp = System.currentTimeMillis(),
                witnesses = 3
            ),
            Report(
                id = "report_2",
                type = "Tráfico",
                description = "Atividade suspeita no parque",
                latitude = -23.5650,
                longitude = -46.6500,
                riskLevel = "Crítico",
                timestamp = System.currentTimeMillis() - 3600000,
                witnesses = 5
            ),
            Report(
                id = "report_3",
                type = "Acidente",
                description = "Colisão entre dois veículos",
                latitude = -23.5500,
                longitude = -46.6700,
                riskLevel = "Médio",
                timestamp = System.currentTimeMillis() - 7200000,
                witnesses = 2
            )
        )
    }

    fun selectReport(report: Report) {
        _selectedReport.value = report
    }

    fun searchLocation(query: String) {
        // Simular busca de localização
        _searchResults.value = listOf(
            SearchResult(
                id = "loc_1",
                name = "São Paulo, SP",
                latitude = -23.5608,
                longitude = -46.6560
            ),
            SearchResult(
                id = "loc_2",
                name = "Avenida Paulista, São Paulo",
                latitude = -23.5615,
                longitude = -46.6559
            ),
            SearchResult(
                id = "loc_3",
                name = "Parque Ibirapuera, São Paulo",
                latitude = -23.5900,
                longitude = -46.6580
            )
        )
    }

    fun filterByRisk(riskLevel: String?) {
        _riskFilter.value = riskLevel
    }

    fun submitReport(report: NewReport) {
        // Simular envio de denúncia para backend
        val newReport = Report(
            id = "report_${System.currentTimeMillis()}",
            type = report.type,
            description = report.description,
            latitude = report.latitude,
            longitude = report.longitude,
            riskLevel = report.riskLevel,
            timestamp = System.currentTimeMillis(),
            witnesses = if (report.isWitness) 1 else 0
        )
        
        _reports.value = _reports.value + newReport
    }
}

data class Report(
    val id: String,
    val type: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val riskLevel: String,
    val timestamp: Long,
    val witnesses: Int
)

data class SearchResult(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double
)

data class NewReport(
    val type: String,
    val riskLevel: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val isWitness: Boolean,
    val mediaUrls: List<String> = emptyList()
)
package com.apolo.app.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.apolo.app.R
import com.apolo.app.viewmodel.AuthViewModel
import com.apolo.app.viewmodel.MapViewModel
import com.apolo.app.viewmodel.SearchResult
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.*
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.*
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource

@Composable
fun HomeScreen(
    mapViewModel: MapViewModel,
    authViewModel: AuthViewModel,
    onNavigateToReport: () -> Unit,
    onNavigateToReportsList: () -> Unit,
    onNavigateToEmergency: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToNotifications: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedRiskFilter by remember { mutableStateOf<String?>(null) }
    var showSearchResults by remember { mutableStateOf(false) }

    val reports = mapViewModel.reports.collectAsState().value
    val searchResults = mapViewModel.searchResults.collectAsState().value
    val currentLocation = mapViewModel.currentLocation.collectAsState().value
    val userHeading = mapViewModel.userHeading.collectAsState().value
    val destination = mapViewModel.destination.collectAsState().value
    val routePoints = mapViewModel.routePoints.collectAsState().value
    val routeDistance = mapViewModel.routeDistanceMeters.collectAsState().value
    val routeDuration = mapViewModel.routeDurationSeconds.collectAsState().value
    val routeSafety = mapViewModel.routeSafety.collectAsState().value
    val isPreviewingRoute = mapViewModel.isRoutePreview.collectAsState().value
    val isNavigating = mapViewModel.isNavigating.collectAsState().value

    val permissionGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            // fallback: still show map
        }
    }

    LaunchedEffect(Unit) {
        if (!permissionGranted) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    val mapView = remember {
        MapView(context)
    }

    val arrowBitmap = remember {
        BitmapFactory.decodeResource(context.resources, R.drawable.custom_arrow_blue)
    }

    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
    }
    val rotationSensor = remember {
        sensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    }

    var styleLoaded by remember { mutableStateOf(false) }

    DisposableEffect(lifecycleOwner, mapView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView.onDestroy()
        }
    }

    LaunchedEffect(mapView) {
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) { loadedStyle: Style ->
            styleLoaded = true

            loadedStyle.addImage("user-arrow", arrowBitmap)

            loadedStyle.apply {
                addSource(
                    geoJsonSource("user-location-source") {
                        feature(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
                    }
                )
                addSource(
                    geoJsonSource("route-source") {
                        featureCollection(FeatureCollection.fromFeatures(emptyArray()))
                    }
                )
                addSource(
                    geoJsonSource("destination-source") {
                        feature(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
                    }
                )

                addLayer(
                    lineLayer("route-layer", "route-source") {
                        lineColor("#2962FF")
                        lineWidth(6.0)
                        lineJoin(LineJoin.ROUND)
                        lineCap(LineCap.ROUND)
                        lineOpacity(0.85)
                    }
                )
                addLayer(
                    symbolLayer("destination-layer", "destination-source") {
                        iconImage("user-arrow")
                        iconAllowOverlap(true)
                        iconIgnorePlacement(true)
                        iconSize(0.9)
                    }
                )
                addLayer(
                    symbolLayer("user-layer", "user-location-source") {
                        iconImage("user-arrow")
                        iconAllowOverlap(true)
                        iconIgnorePlacement(true)
                        iconRotate(Expression.get("bearing"))
                        iconSize(1.2)
                    }
                )
            }
        }
    }

    DisposableEffect(rotationSensor) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                    val rotationMatrix = FloatArray(9)
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                    val orientation = FloatArray(3)
                    SensorManager.getOrientation(rotationMatrix, orientation)
                    val azimuthDegrees = ((Math.toDegrees(orientation[0].toDouble()) + 360.0) % 360.0).toFloat()
                    mapViewModel.updateHeading(azimuthDegrees)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        rotationSensor?.let { sensorManager?.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI) }

        onDispose {
            sensorManager?.unregisterListener(listener)
        }
    }

    DisposableEffect(permissionGranted) {
        if (permissionGranted) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.lastLocation?.let { location ->
                        mapViewModel.updateLocation(location)
                    }
                }
            }

            val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000L)
                .setMinUpdateIntervalMillis(1000L)
                .setMaxUpdateDelayMillis(5000L)
                .build()

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let { mapViewModel.updateLocation(it) }
                }
            }

            onDispose {
                fusedLocationClient.removeLocationUpdates(locationCallback)
            }
        } else {
            onDispose {}
        }
    }

    LaunchedEffect(styleLoaded, currentLocation, userHeading, routePoints, destination, isNavigating) {
        if (!styleLoaded) return@LaunchedEffect

        mapView.getMapboxMap().getStyle { style ->
            currentLocation?.let { location ->
                val userFeature = Feature.fromGeometry(
                    Point.fromLngLat(location.longitude, location.latitude)
                ).apply {
                    addNumberProperty("bearing", userHeading.toDouble())
                }

                style.getSourceAs<GeoJsonSource>("user-location-source")?.feature(userFeature)

                if (isNavigating) {
                    val cameraOptions = CameraOptions.Builder()
                        .center(Point.fromLngLat(location.longitude, location.latitude))
                        .bearing(userHeading.toDouble())
                        .pitch(58.0)
                        .zoom(16.5)
                        .padding(EdgeInsets(220.0, 0.0, 100.0, 0.0))
                        .build()
                    mapView.getMapboxMap().setCamera(cameraOptions)
                } else {
                    val cameraOptions = CameraOptions.Builder()
                        .center(Point.fromLngLat(location.longitude, location.latitude))
                        .zoom(14.0)
                        .build()
                    mapView.getMapboxMap().setCamera(cameraOptions)
                }
            }

            val routeSource = style.getSourceAs<GeoJsonSource>("route-source")
            if (isNavigating && routePoints.isNotEmpty()) {
                routeSource?.featureCollection(
                    FeatureCollection.fromFeatures(
                        arrayOf(Feature.fromGeometry(LineString.fromLngLats(routePoints)))
                    )
                )
            } else {
                routeSource?.featureCollection(FeatureCollection.fromFeatures(emptyArray()))
            }

            destination?.let { dest ->
                val destinationFeature = Feature.fromGeometry(Point.fromLngLat(dest.longitude, dest.latitude))
                style.getSourceAs<GeoJsonSource>("destination-source")?.feature(destinationFeature)
            } ?: style.getSourceAs<GeoJsonSource>("destination-source")?.feature(
                Feature.fromGeometry(Point.fromLngLat(0.0, 0.0))
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { mapView }, modifier = Modifier.fillMaxSize())

        Column {
            TopBar(
                searchQuery = searchQuery,
                onSearchChange = { query ->
                    searchQuery = query
                    if (query.isNotEmpty()) {
                        mapViewModel.searchLocation(query)
                        showSearchResults = true
                    } else {
                        showSearchResults = false
                    }
                },
                onSettingsClick = onNavigateToSettings,
                onCameraClick = { /* not implemented */ },
                onNotificationsClick = onNavigateToNotifications
            )

            if (showSearchResults && searchResults.isNotEmpty()) {
                SearchResultsList(
                    results = searchResults,
                    onItemClick = { result ->
                        showSearchResults = false
                        searchQuery = result.name
                        mapViewModel.previewNavigationTo(result, getMapboxToken(context))
                    }
                )
            }

            if (!showSearchResults) {
                RiskChipsRow(
                    selectedRisk = selectedRiskFilter,
                    onRiskSelected = { risk ->
                        selectedRiskFilter = if (selectedRiskFilter == risk) null else risk
                        mapViewModel.filterByRisk(selectedRiskFilter)
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isPreviewingRoute && destination != null) {
                RoutePreviewCard(
                    destinationName = destination.name,
                    distanceMeters = routeDistance,
                    durationSeconds = routeDuration,
                    safetyLabel = routeSafety,
                    onFollowRoute = { mapViewModel.confirmNavigation() }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            if (reports.isNotEmpty()) {
                Text(
                    "📍 ${reports.size} ocorrências próximas",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ExtendedFloatingActionButton(
                    onClick = onNavigateToReport,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    containerColor = Color(0xFFFDD835),
                    contentColor = Color.Black
                ) {
                    Icon(Icons.Filled.Notifications, contentDescription = "Denunciar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Denunciar")
                }

                FloatingActionButton(
                    onClick = onNavigateToEmergency,
                    modifier = Modifier.size(56.dp),
                    containerColor = Color(0xFFE53935),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Warning, contentDescription = "Emergência", modifier = Modifier.size(24.dp))
                }
            }

            if (reports.isNotEmpty()) {
                BottomStrip(
                    reportCount = reports.size,
                    onViewAll = onNavigateToReportsList
                )
            }
        }
    }
}

private fun getMapboxToken(context: Context): String {
    return try {
        val appInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        appInfo.metaData?.getString("com.mapbox.AccessToken") ?: ""
    } catch (exception: Exception) {
        ""
    }
}

@Composable
fun TopBar(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onCameraClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                placeholder = { Text("Buscar localização...", fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            IconButton(onClick = onCameraClick) {
                Icon(Icons.Filled.CameraAlt, contentDescription = "Camera")
            }

            IconButton(onClick = onSettingsClick) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings")
            }

            IconButton(onClick = onNotificationsClick) {
                Icon(Icons.Filled.Notifications, contentDescription = "Notifications")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiskChipsRow(
    selectedRisk: String?,
    onRiskSelected: (String) -> Unit
) {
    val risks = listOf(
        Risk("Seguro", Color(0xFF4CAF50)),
        Risk("Atenção", Color(0xFFFFEB3B)),
        Risk("Moderado", Color(0xFFFF9800)),
        Risk("Alto Risco", Color(0xFFF44336))
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(risks) { risk ->
            FilterChip(
                selected = selectedRisk == risk.name,
                onClick = { onRiskSelected(risk.name) },
                label = { Text(risk.name, fontSize = 12.sp) },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = risk.color.copy(alpha = 0.1f),
                    selectedContainerColor = risk.color.copy(alpha = 0.3f),
                    labelColor = risk.color
                )
            )
        }
    }
}

@Composable
fun SearchResultsList(
    results: List<SearchResult>,
    onItemClick: (SearchResult) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 12.dp),
        shadowElevation = 8.dp
    ) {
        Column {
            results.forEach { result ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(result) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.LocationOn, contentDescription = "", modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(result.name, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("${result.latitude}, ${result.longitude}", fontSize = 10.sp, color = Color.Gray)
                    }
                }
                Divider()
            }
        }
    }
}

@Composable
fun BottomStrip(
    reportCount: Int,
    onViewAll: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        color = Color(0xFFFFF3E0),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Warning, contentDescription = "", tint = Color(0xFFFFA500))
                Spacer(modifier = Modifier.width(8.dp))
                Text("$reportCount ocorrências próximas", fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
            TextButton(onClick = onViewAll) {
                Text("Ver todas", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun RoutePreviewCard(
    destinationName: String,
    distanceMeters: Double,
    durationSeconds: Double,
    safetyLabel: String,
    onFollowRoute: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .shadow(16.dp, RoundedCornerShape(24.dp), clip = false),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        tonalElevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Seguindo para", fontSize = 12.sp, color = Color(0xFF9E9E9E), fontWeight = FontWeight.Medium)
                Text(
                    destinationName,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF212121)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PreviewTile(modifier = Modifier.weight(1f), label = "DISTÂNCIA", value = formatDistance(distanceMeters), icon = Icons.Filled.Place)
                PreviewTile(modifier = Modifier.weight(1f), label = "TEMPO", value = formatDuration(durationSeconds), icon = Icons.Filled.Timer)
                PreviewTile(modifier = Modifier.weight(1f), label = "SEGURANÇA", value = safetyLabel, icon = Icons.Filled.Security)
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onFollowRoute,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.White)
                Spacer(modifier = Modifier.width(10.dp))
                Text("Seguir Caminho", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun PreviewTile(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: ImageVector
) {
    Surface(
        modifier = modifier
            .height(90.dp),
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier
                .padding(11.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = label, tint = Color(0xFF2196F3), modifier = Modifier.size(22.dp))
            Column {
                Text(label, fontSize = 9.sp, color = Color(0xFFBDBDBD), fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                Spacer(modifier = Modifier.height(3.dp))
                Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF212121))
            }
        }
    }
}

private fun formatDistance(distanceMeters: Double): String {
    return when {
        distanceMeters <= 0.0 -> "Calculando..."
        distanceMeters >= 1000.0 -> "${String.format("%.1f", distanceMeters / 1000.0)} km"
        else -> "${distanceMeters.toInt()} m"
    }
}

private fun formatDuration(durationSeconds: Double): String {
    if (durationSeconds <= 0.0) return "Calculando..."
    val minutes = (durationSeconds / 60).toInt()
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return if (hours > 0) {
        "${hours}h ${remainingMinutes}m"
    } else {
        "${remainingMinutes} min"
    }
}

data class Risk(
    val name: String,
    val color: Color
)

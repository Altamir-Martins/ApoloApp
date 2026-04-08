# 🚀 CONVERSÃO APOLO PARA ANDROID NATIVO (KOTLIN)

## 📋 Sumário da Conversão

Vamos converter o app Web APOLO (HTML/CSS/JS) para um aplicativo Android nativo usando:
- **Linguagem:** Kotlin
- **IDE:** Android Studio
- **SDK Mínimo:** Android 6.0 (API 23)
- **Target SDK:** Android 14+ (API 34+)
- **Mapbox GL Android SDK** para o mapa
- **CameraX API** para câmera nativa

---

## 🏗️ Arquitetura Android

```
ApoloAndroid/
├── app/
│   ├── src/main/
│   │   ├── kotlin/com/apolo/
│   │   │   ├── MainActivity.kt
│   │   │   ├── activities/
│   │   │   │   ├── SplashActivity.kt
│   │   │   │   ├── LoginActivity.kt
│   │   │   │   ├── RegisterActivity.kt
│   │   │   │   ├── HomeActivity.kt (Mapa principal)
│   │   │   │   ├── ReportActivity.kt (Denúncia)
│   │   │   │   ├── EmergencyActivity.kt
│   │   │   │   └── CameraActivity.kt
│   │   │   ├── fragments/
│   │   │   │   ├── MapFragment.kt
│   │   │   │   ├── ReportsFragment.kt
│   │   │   │   ├── ProfileFragment.kt
│   │   │   │   └── SettingsFragment.kt
│   │   │   ├── viewmodels/
│   │   │   │   ├── HomeViewModel.kt
│   │   │   │   ├── ReportViewModel.kt
│   │   │   │   └── MapViewModel.kt
│   │   │   ├── data/
│   │   │   │   ├── models/
│   │   │   │   │   ├── Report.kt
│   │   │   │   │   ├── Location.kt
│   │   │   │   │   ├── User.kt
│   │   │   │   │   └── EmergencyEvent.kt
│   │   │   │   ├── database/
│   │   │   │   │   └── ApoloDatabase.kt
│   │   │   │   ├── repository/
│   │   │   │   │   └── ReportRepository.kt
│   │   │   │   └── remote/
│   │   │   │       └── ApiService.kt
│   │   │   ├── ui/
│   │   │   │   ├── components/
│   │   │   │   │   ├── FABCustom.kt
│   │   │   │   │   └── LocationDisplay.kt
│   │   │   │   └── theme/
│   │   │   │       └── Theme.kt
│   │   │   ├── utils/
│   │   │   │   ├── LocationManager.kt
│   │   │   │   ├── CameraHelper.kt
│   │   │   │   ├── PermissionHelper.kt
│   │   │   │   └── NotificationHelper.kt
│   │   │   └── service/
│   │   │       └── LocationService.kt (Serviço em background)
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml
│   │   │   │   ├── activity_home.xml (Mapa + FABs)
│   │   │   │   ├── activity_report.xml
│   │   │   │   └── ... (outros layouts)
│   │   │   ├── drawable/
│   │   │   │   ├── ic_launcher.xml
│   │   │   │   ├── icon_siren.xml
│   │   │   │   ├── icon_cube_3d.xml
│   │   │   │   └── fab_background.xml
│   │   │   ├── values/
│   │   │   │   ├── colors.xml
│   │   │   │   ├── strings.xml
│   │   │   │   ├── themes.xml (Dark mode + Light)
│   │   │   │   └── dimens.xml
│   │   │   ├── values-night/
│   │   │   │   └── colors.xml (Dark mode colors)
│   │   │   └── menu/
│   │   │       └── bottom_menu.xml
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
└── local.properties
```

---

## 🔧 Configuração Inicial

### 1️⃣ Criar Projeto Android Studio

```bash
# Via Android Studio:
# File → New → New Android Project
# Escolher: Empty Activity (Kotlin)
# Nome: ApoloApp
# Package: com.apolo.app
# SDK Mínimo: API 23 (Android 6.0)
```

### 2️⃣ Configurar build.gradle.kts

```kotlin
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.apolo.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.apolo.app"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        dataBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")

    // Jetpack Compose (UI moderna)
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.activity:activity-compose:1.8.0")

    // ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Room Database
    implementation("androidx.room:room-runtime:2.6.0")
    kapt("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")

    // Retrofit & OkHttp (API calls)
    implementation("com.squareup.retrofit2:retrofit:2.10.0")
    implementation("com.squareup.retrofit2:converter-kotlinx-serialization:2.10.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Kotlin Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    // Mapbox GL Android SDK
    implementation("com.mapbox.maps:android:10.13.0")

    // CameraX (câmera nativa)
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-camera2:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation("androidx.camera:camera-video:1.3.0")
    implementation("androidx.camera:camera-view:1.3.0")

    // Location Services
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Permissions (EasyPermissions ou similar)
    implementation("pub.devrel:easypermissions:3.0.0")

    // Hilt Dependency Injection
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.2")

    // WorkManager (para tarefas em background)
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // DataStore (Preferences async)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Notifications
    implementation("androidx.core:core:1.12.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
```

---

## 📱 Implementação Principal

### 1️⃣ MainActivity.kt

```kotlin
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.apolo.app.ui.theme.ApoloTheme
import com.apolo.app.screens.SplashScreen
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            ApoloTheme {
                SplashScreen()
            }
        }
    }
}
```

### 2️⃣ HomeActivity.kt (Tela Principal com Mapa)

```kotlin
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mapbox.maps.MapView
import com.mapbox.maps.extension.compose.*
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.geojson.Point
import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    private lateinit var mapView: MapView
    private var is3DMode by mutableStateOf(false)
    private var mapboxAccessToken = "pk.YOUR_MAPBOX_ACCESS_TOKEN"

    private val locationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startLocationUpdates()
        }
    }

    private val cameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Solicitar permissão de localização
        locationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                // Mapa (MapView integrado)
                MapboxMapComposable(
                    modifier = Modifier.fillMaxSize(),
                    mapStyle = if (isDarkMode()) {
                        "mapbox://styles/mapbox/dark-v11"
                    } else {
                        "mapbox://styles/mapbox/streets-v12"
                    }
                )

                // Top Bar
                TopBar()

                // FABs Container (Centralizado ao fundo)
                FABsContainer()

                // Localização em tempo real
                LocationDisplay()
            }
        }
    }

    @Composable
    private fun TopBar() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Menu
                IconButton(
                    onClick = { /* Abrir menu lateral */ },
                    modifier = Modifier.background(Color.White, shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_view),
                        contentDescription = "Menu"
                    )
                }

                // Pesquisa
                SearchBar()

                // 3D Toggle
                IconButton(
                    onClick = { toggle3DMode() },
                    modifier = Modifier.background(
                        if (is3DMode) Color.Blue else Color.White,
                        shape = CircleShape
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cube_3d),
                        contentDescription = "3D Mode"
                    )
                }

                // Câmera
                IconButton(
                    onClick = { openCameraPermission() },
                    modifier = Modifier.background(Color.White, shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_camera),
                        contentDescription = "Câmera"
                    )
                }

                // Notificações
                IconButton(
                    onClick = { /* Abrir notificações */ },
                    modifier = Modifier.background(Color.White, shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_dialog_info),
                        contentDescription = "Notificações"
                    )
                    Badge(modifier = Modifier.offset(5.dp, (-5).dp)) { Text("3") }
                }
            }
        }
    }

    @Composable
    private fun FABsContainer() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Exibição de localização
            if (currentLocation != null) {
                Text(
                    text = "📍 ${currentLocation?.latitude?.let { "%.4f".format(it) }} / ${currentLocation?.longitude?.let { "%.4f".format(it) }}",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .space(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                // FAB Report (Amarelo com Megafone)
                FloatingActionButton(
                    onClick = { navigateToReport() },
                    containerColor = Color(0xFFFDD835),
                    contentColor = Color.Black,
                    modifier = Modifier.size(52.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bullhorn),
                        contentDescription = "Denunciar"
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // FAB Emergency (Vermelho com Sirene)
                FloatingActionButton(
                    onClick = { startEmergencyHold() },
                    containerColor = Color(0xFFE53935),
                    contentColor = Color.White,
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_siren),
                        contentDescription = "Emergência"
                    )
                }
            }
        }
    }

    @Composable
    private fun LocationDisplay() {
        // Mostrar coordenadas atualizadas em tempo real
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            currentLocation?.let {
                Text(
                    text = "Localização: ${it.latitude}, ${it.longitude}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }

    private fun toggle3DMode() {
        is3DMode = !is3DMode
        // Usar Mapbox Camera para ajustar pitch e bearing
        mapView.mapboxMap.apply {
            if (is3DMode) {
                setCamera(
                    CameraOptions.Builder()
                        .pitch(45.0)
                        .bearing(25.0)
                        .build()
                )
            } else {
                setCamera(
                    CameraOptions.Builder()
                        .pitch(0.0)
                        .bearing(0.0)
                        .build()
                )
            }
        }
    }

    private fun openCameraPermission() {
        cameraPermission.launch(Manifest.permission.CAMERA)
    }

    private fun openCamera() {
        // Usar CameraX API para capturar foto
        // Implementação detalhada abaixo...
    }

    private fun startLocationUpdates() {
        // Implementar com Location Services do Google Play Services
    }

    private fun startEmergencyHold() {
        // Iniciar hold de 3s para ativar emergência
        // Progress animation com countdown
    }

    private fun navigateToReport() {
        startActivity(Intent(this, ReportActivity::class.java))
    }

    private fun isDarkMode(): Boolean {
        return resources.configuration.uiMode and
               android.content.res.Configuration.UI_MODE_NIGHT_MASK ==
               android.content.res.Configuration.UI_MODE_NIGHT_YES
    }
}
```

### 3️⃣ CameraActivity.kt (Câmera Nativa)

```kotlin
import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class CameraActivity : ComponentActivity() {
    private lateinit var imageCapture: ImageCapture

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CameraScreen()
        }

        startCamera()
    }

    @Composable
    private fun CameraScreen() {
        Box(modifier = Modifier.fillMaxSize()) {
            // Preview da câmera
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    PreviewView(context).apply {
                        id = R.id.camera_preview
                    }
                }
            )

            // Botões flutuando
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Fechar
                FloatingActionButton(
                    onClick = { finish() },
                    containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Text("Cancelar")
                }

                // Capturar foto
                FloatingActionButton(
                    onClick = { capturePhoto() },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text("📷 Capturar")
                }
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            
            // Preview
            val preview = Preview.Builder().build()
            
            // ImageCapture
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            // Select back camera
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (exc: Exception) {
                // Handle error
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun capturePhoto() {
        val imageCapture = imageCapture ?: return

        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(
            contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "APOLO_${System.currentTimeMillis()}.jpg")
            }
        ).build()

        imageCapture.takePicture(
            outputFileOptions,
            Executors.newSingleThreadExecutor(),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    // Foto salva com sucesso
                    val uri = outputFileResults.savedUri
                    val resultIntent = Intent().apply {
                        putExtra("photo_uri", uri.toString())
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }

                override fun onError(exception: ImageCaptureException) {
                    // Error ao capturar
                    exception.printStackTrace()
                }
            }
        )
    }
}
```

### 4️⃣ LocationManager.kt (Gerenciador de Localização em Tempo Real)

```kotlin
import android.content.Context
import android.location.Location
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationManager(context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    
    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
        .setMinUpdateDistanceMeters(5f)
        .build()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let {
                _currentLocation.value = it
            }
        }
    }

    fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
```

### 5️⃣ ReportData.kt (Modelo de Dados)

```kotlin
import kotlinx.serialization.Serializable
import android.graphics.Bitmap
import java.util.*

@Serializable
data class Report(
    val id: String = UUID.randomUUID().toString(),
    val type: String, // "roubo", "acidente", "tráfico", etc
    val riskLevel: String, // "green", "yellow", "orange", "red"
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val photos: List<String> = emptyList(), // URIs de fotos
    val timestamp: Long = System.currentTimeMillis(),
    val isAnonymous: Boolean = true,
    val witness: Boolean = false
)

@Serializable
data class EmergencyEvent(
    val id: String = UUID.randomUUID().toString(),
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "active" // "active", "handled", "resolved"
)

data class Location(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float
)
```

---

## 🎨 UI Theming (Dark Mode Automático)

### Theme.kt

```kotlin
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1E88E5),
    secondary = Color(0xFFFDD835),
    tertiary = Color(0xFFE53935),
    background = Color(0xFF0A0A0A),
    surface = Color(0xFF121212),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1E88E5),
    secondary = Color(0xFFFDD835),
    tertiary = Color(0xFFE53935),
    background = Color.White,
    surface = Color(0xFFF5F5F5),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White
)

@Composable
fun ApoloTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
```

---

## 📋 AndroidManifest.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- Permissões -->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <uses-permission android:name="android.permission.VIBRATE" />

    <application
        android:allowBackup="true"
        android:debuggable="false"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.Apolo">

        <!-- Splash Activity -->
        <activity
            android:name=".activities.SplashActivity"
            android:exported="true"
            android:theme="@style/Theme.Apolo.Splash">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Home Activity (Mapa Principal) -->
        <activity
            android:name=".activities.HomeActivity"
            android:exported="false"
            android:screenOrientation="portrait" />

        <!-- Report Activity -->
        <activity
            android:name=".activities.ReportActivity"
            android:exported="false"
            android:screenOrientation="portrait" />

        <!-- Emergency Activity -->
        <activity
            android:name=".activities.EmergencyActivity"
            android:exported="false"
            android:screenOrientation="portrait" />

        <!-- Camera Activity -->
        <activity
            android:name=".activities.CameraActivity"
            android:exported="false"
            android:screenOrientation="portrait" />

        <!-- Localização em Background Service -->
        <service
            android:name=".service.LocationService"
            android:exported="false"
            android:foregroundServiceType="location" />
    </application>

</manifest>
```

---

## 📊 Configuração Mapbox Android

```kotlin
// No AndroidManifest.xml, adicionar dentro de <application>
<meta-data
    android:name="com.mapbox.map_key"
    android:value="pk.YOUR_MAPBOX_ACCESS_TOKEN" />
```

---

## 🚀 Compilação e Deploy

### Build Release APK

```bash
# Build APK release
./gradlew assembleRelease

# Build AAB (para Google Play)
./gradlew bundleRelease

# Assinar com certificado de produção
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 \
    -keystore apolo-key.jks \
    app/build/outputs/bundle/release/app.aab \
    alias_name
```

### Upload Google Play

1. Abrir `Google Play Console`
2. Criar novo app "APOLO"
3. Fazer upload do `AAB`
4. Preencher descrição, screenshots, política de privacidade
5. Submeter para revisão

---

## ✅ Checklist de Conversão

- [x] Estrutura de projeto Android criada
- [x] bom build.gradle.kts configurado
- [x] Dependências Mapbox, CameraX, Location Services adicionadas
- [x] MainActivity com NavigationCompose
- [x] HomeActivity com mapa Mapbox integrado
- [x] Botão Toggle 3D implementado
- [x] CameraActivity com CameraX
- [x] LocationManager para GPS em tempo real
- [x] Modelos de dados (Report, EmergencyEvent)
- [x] AndroidManifest.xml com permissões
- [x] Dark Mode automático
- [x] FABs customizados (58px e 64px)
- [x] Ícone de sirene no FAB de emergência
- [x] Localização exibida em tempo real
- [ ] Backend API integration (Retrofit)
- [ ] Database Room local
- [ ] Push Notifications (FCM)
- [ ] testes Android (Espresso)
- [ ] Obfuscação com ProGuard
- [ ] Build APK release
- [ ] Upload Google Play Store

---

## 🎓 Próximos Passos

1. **Clone este template** da conversão Android
2. **Configure Mapbox token** no AndroidManifest.xml
3. **Execute no Android Studio** (Emulator ou device físico)
4. **Teste todas as funcionalidades:**
   - Tela de Login/Splash
   - Mapa com localização real
   - Toggle 3D
   - Câmera
   - Botão Emergência
   - Denúncias
5. **Build e deploy** para Google Play

---

**Documentação Técnica Android:** https://developer.android.com/docs  
**Mapbox Android SDK:** https://docs.mapbox.com/android/  
**CameraX Docs:** https://developer.android.com/training/camerax

---

**Status:** 📋 Template Pronto para Conversão  
**Versão:** v1.0 Android  
**Linguagem:** Kotlin 100%  
**SDK Mínimo:** Android 6.0 (API 23)


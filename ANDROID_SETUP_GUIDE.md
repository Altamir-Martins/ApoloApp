# 🚀 GUIA: CRIAR PROJETO ANDROID APOLO EM KOTLIN

## 📋 Pré-requisitos

Antes de começar, você precisa ter instalado:

- **Android Studio** (versão 2024.1+)
  - Download: https://developer.android.com/studio
  - Instalar com os emuladores padrão

- **SDK Versões:**
  - Min SDK: Android 6.0 (API 23)
  - Target SDK: Android 14+ (API 34)
  - Compilação: API 35

- **Java/Kotlin:**
  - Kotlin: 1.9.0+
  - JDK: 17 ou superior

---

## 🎯 PASSO 1: Criar Novo Projeto

### No Android Studio:
```
1. File → New → New Android Project
2. Escolher template: "Empty Activity"
3. Preencher informações:

   Name: ApoloApp
   Package: com.apolo.app
   Save location: C:\Users\[seu_user]\AndroidStudioProjects\ApoloApp
   Language: Kotlin
   Minimum SDK: API 23 (Android 6.0)
   Theme: Material 3
```

### Estrutura criada automaticamente:
```
ApoloApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── java/com/apolo/app/
│   │   │   │   └── MainActivity.kt (renomear para HomeActivity.kt)
│   │   │   └── res/
│   │   │       ├── drawable/
│   │   │       ├── layout/
│   │   │       ├── values/
│   │   │       └── mipmap/
│   │   ├── test/
│   │   └── androidTest/
│   └── build.gradle.kts
├── gradle/wrapper/
├── .gitignore
├── README.md
└── settings.gradle.kts
```

---

## 📦 PASSO 2: Configurar build.gradle.kts

### Localizar: `app/build.gradle.kts`

### Substituir TODO o conteúdo por:

```kotlin
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.apolo.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.apolo.app"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0-beta1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        // Mapbox token
        manifestPlaceholders["MAPBOX_ACCESS_TOKEN"] = "pk.YOUR_MAPBOX_ACCESS_TOKEN"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}

dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.1")

    // Jetpack Compose (Material 3)
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.1")

    // Mapbox GL Android
    implementation("com.mapbox.maps:android:10.13.0")
    implementation("com.mapbox.maps:compose:10.13.0")
    implementation("com.mapbox.extension:maps-compose:11.0.0") // Optional

    // Camera (CameraX)
    implementation("androidx.camera:camera-core:1.3.1")
    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")
    implementation("androidx.camera:camera-extensions:1.3.1")

    // Location Services
    implementation("com.google.android.gms:play-services-location:21.1.0")

    // Networking (Retrofit)
    implementation("com.squareup.retrofit2:retrofit:2.10.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    // Database (Room)
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Dependency Injection (Hilt)
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // WorkManager (Background tasks)
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // DataStore (Preferences)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
```

### Arquivo: `settings.gradle.kts`

```kotlin
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                username = "mapbox"
                password = "sk.eyJ1IjoiYWx0YW1pcm1hcnRpbnMiLCJhIjoiY21ucWlyZmJjMDdxcDIifQ.V3JvbmdLZXk" // MAPBOX SECRET KEY
            }
        }
    }
}

rootProject.name = "ApoloApp"
include(":app")
```

---

## 🎨 PASSO 3: Criar Estrutura de Pastas

### Dentro de `app/src/main/java/com/apolo/app/`, criar:

```
com/apolo/app/
├── activities/
│   ├── HomeActivity.kt
│   ├── CameraActivity.kt
│   ├── ReportActivity.kt
│   ├── EmergencyActivity.kt
│   └── LoginActivity.kt
├── viewmodels/
│   ├── HomeViewModel.kt
│   ├── CameraViewModel.kt
│   └── SharedViewModel.kt
├── models/
│   ├── Report.kt
│   ├── EmergencyEvent.kt
│   ├── Location.kt
│   └── User.kt
├── data/
│   ├── dao/
│   │   ├── ReportDao.kt
│   │   └── EmergencyEventDao.kt
│   ├── database/
│   │   └── ApoloDatabase.kt
│   └── repository/
│       ├── ReportRepository.kt
│       └── LocationRepository.kt
├── ui/
│   ├── components/
│   │   ├── FABComponent.kt
│   │   ├── LocationDisplay.kt
│   │   └── EmergencyButton.kt
│   ├── screens/
│   │   ├── HomeScreen.kt
│   │   ├── CameraScreen.kt
│   │   └── ReportScreen.kt
│   └── theme/
│       ├── Theme.kt
│       ├── Color.kt
│       └── Typography.kt
├── utils/
│   ├── LocationManager.kt
│   ├── CameraHelper.kt
│   ├── PermissionHelper.kt
│   ├── Constants.kt
│   └── Converters.kt
├── di/
│   ├── AppModule.kt
│   ├── DatabaseModule.kt
│   └── LocationModule.kt
└── MainActivity.kt (renomear para App.kt ou remover)
```

---

## 🔧 PASSO 4: Sync Gradle

**No Android Studio:**
```
1. File → Sync Now (ou Ctrl+Shift+S)
2. Aguardar download de todas as dependências
3. Verificar se não houver erros
4. Consola deve mostrar: "Build completed successfully"
```

---

## 📝 PASSO 5: Criar HomeActivity.kt

### Arquivo: `app/src/main/java/com/apolo/app/activities/HomeActivity.kt`

```kotlin
package com.apolo.app.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.scalebar.scalebar
import dagger.hilt.android.AndroidEntryPoint
import com.apolo.app.ui.theme.ApoloAppTheme
import com.apolo.app.viewmodels.HomeViewModel

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApoloAppTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    var is3DMode by remember { mutableStateOf(false) }
    var userLocation by remember { mutableStateOf<Pair<Double, Double>?>(null) }
    
    LaunchedEffect(Unit) {
        viewModel.startLocationUpdates { lat, lon ->
            userLocation = Pair(lat, lon)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        // Mapbox Map será renderizado aqui
        MapboxMapComposable(
            modifier = Modifier.fillMaxSize(),
            is3DMode = is3DMode,
            userLocation = userLocation
        )

        // Top Bar
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Menu Button
            IconButton(
                onClick = { /* TODO: Abrir drawer */ }
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }

            // 3D Toggle Button
            IconButton(
                onClick = { is3DMode = !is3DMode }
            ) {
                Icon(
                    imageVector = Icons.Default.Cube,
                    contentDescription = "3D Mode",
                    tint = if (is3DMode) MaterialTheme.colorScheme.primary else LocalContentColor.current
                )
            }

            // Camera Button
            IconButton(
                onClick = { /* TODO: Abrir câmera */ }
            ) {
                Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = "Camera")
            }

            // Notifications
            BadgedBox(
                badge = { Badge { Text("3") } }
            ) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
            }
        }

        // Location Display (Centered)
        if (userLocation != null) {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 100.dp)
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceContainer,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "%.4f, %.4f".format(userLocation!!.first, userLocation!!.second),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }

        // FABs Centralizados
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // FAB Report (Amarelo)
            FloatingActionButton(
                onClick = { /* TODO: Abrir ReportActivity */ },
                containerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(52.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Report",
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // FAB Emergency (Vermelho)
            FloatingActionButton(
                onClick = { /* TODO: Abrir EmergencyActivity */ },
                containerColor = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(64.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.Siren,
                        contentDescription = "Emergency",
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
            )
        }
    }
}

@Composable
fun MapboxMapComposable(
    modifier: Modifier = Modifier,
    is3DMode: Boolean = false,
    userLocation: Pair<Double, Double>? = null
) {
    // Implementação do Mapbox Maps Compose aqui
    // Ver ANDROID_KOTLIN_CONVERSION.md para código completo
}
```

---

## 🎨 PASSO 6: Criar Theme.kt

### Arquivo: `app/src/main/java/com/apolo/app/ui/theme/Theme.kt`

```kotlin
package com.apolo.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
)

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
)

@Composable
fun ApoloAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

---

## 📝 PASSO 7: Atualizar AndroidManifest.xml

### Arquivo: `app/src/main/AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- Permissions -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

    <!-- Features (optional but recommended) -->
    <uses-feature
        android:name="android.hardware.camera"
        android:required="false" />
    <uses-feature
        android:name="android.hardware.location.gps"
        android:required="false" />

    <application
        android:name=".App"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/Theme.ApoloApp"
        tools:targetApi="35">

        <!-- Mapbox Token -->
        <meta-data
            android:name="com.mapbox.maps.accessToken"
            android:value="pk.YOUR_MAPBOX_ACCESS_TOKEN" />

        <!-- Activities -->
        <activity
            android:name=".activities.HomeActivity"
            android:exported="true"
            android:screenOrientation="portrait">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <activity
            android:name=".activities.CameraActivity"
            android:screenOrientation="portrait" />

        <activity
            android:name=".activities.ReportActivity"
            android:screenOrientation="portrait" />

        <activity
            android:name=".activities.EmergencyActivity"
            android:screenOrientation="portrait" />

        <activity
            android:name=".activities.LoginActivity"
            android:screenOrientation="portrait" />

    </application>

</manifest>
```

---

## ▶️ PASSO 8: Build e Run

### No Android Studio:

```
1. Certificar que o arquivo está sincronizado (build.gradle.kts salvo)
2. Build → Clean Project (Ctrl+Shift+B)
3. Build → Rebuild Project
4. Run → Run 'app' (Shift+F10)
5. Selecionar emulador ou device conectado
6. Aguardar build e instalação
```

### Se houver erros:

```
❌ "Cannot resolve symbol..."
   → Fazer Sync Now (Ctrl+Shift+S)

❌ "Failed to find property 'mapbox'"
   → Verificar settings.gradle.kts com credenciais Mapbox

❌ "compileSdk version too low..."
   → Aumentar para 35 em build.gradle.kts

❌ Permission denied...
   → Ir a Settings → Apps → ApoloApp → Permissions
   → Permitir Location e Camera
```

---

## ✅ Checklist de Conclusão

- [ ] Android Studio 2024.1+ instalado
- [ ] Novo projeto criado (ApoloApp, com.apolo.app)
- [ ] build.gradle.kts atualizado com todas as dependências
- [ ] settings.gradle.kts com Mapbox Maven configured
- [ ] Estrutura de pastas criada (activities, viewmodels, models, etc)
- [ ] HomeActivity.kt implementado
- [ ] Theme.kt criado com Material 3
- [ ] AndroidManifest.xml com permissions e Mapbox token
- [ ] Gradle sincronizado sem erros
- [ ] App compilou e rodou em emulador/device
- [ ] HomeScreen renderiza com mapa Mapbox
- [ ] FABs e buttons visíveis e responsivos
- [ ] Localização em tempo real funcionando

---

## 📚 Próximas Etapas

Após concluir acima:

1. **Implementar CameraActivity.kt**
   - Ver ANDROID_KOTLIN_CONVERSION.md → CameraActivity Section

2. **Implementar ReportActivity.kt**
   - Form com título, descrição, foto, localização
   - Salvando dados em Room Database

3. **Implementar EmergencyActivity.kt**
   - Hold-to-confirm com circular progress
   - Enviar emergência via API (quando backend estiver pronto)

4. **Integrar Retrofit para Backend**
   - Criar API service
   - Endpoints para upload de relatórios
   - Endpoints para emergências

5. **Testes e Deploy**
   - Build release APK
   - Testar em dispositivos reais
   - Upload para Google Play Store

---

**Versão:** 1.0 Android Setup Guide  
**Data:** Abril 2026  
**Status:** Ready to implement ✅


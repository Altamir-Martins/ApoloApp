package com.apolo.app

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apolo.app.ui.screens.*
import com.apolo.app.ui.theme.ApoloTheme
import com.apolo.app.viewmodel.AuthViewModel
import com.apolo.app.viewmodel.MapViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var authViewModel: AuthViewModel
    private lateinit var mapViewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("apolo_prefs", MODE_PRIVATE)

        // Inicializar Mapbox Token via AndroidManifest meta-data

        // Criar ViewModels
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        setContent {
            ApoloTheme {
                AppNavigation(
                    authViewModel = authViewModel,
                    mapViewModel = mapViewModel,
                    sharedPreferences = sharedPreferences
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel,
    mapViewModel: MapViewModel,
    sharedPreferences: SharedPreferences
) {
    val navController = rememberNavController()
    val isFirstTime = sharedPreferences.getBoolean("first_time", true)
    val isLoggedIn = authViewModel.isLoggedIn.collectAsState().value
    val startDestination = when {
        isFirstTime -> "splash"
        !isLoggedIn -> "login"
        else -> "home"
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        // Splash Screen
        composable("splash") {
            SplashScreen(
                onComplete = {
                    navController.navigate("onboarding") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        // Onboarding
        composable("onboarding") {
            OnboardingScreen(
                onComplete = {
                    sharedPreferences.edit().putBoolean("first_time", false).apply()
                    navController.navigate("login") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }

        // Login
        composable("login") {
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // Cadastro
        composable("register") {
            RegisterScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        // Home (Mapa)
        composable("home") {
            HomeScreen(
                mapViewModel = mapViewModel,
                authViewModel = authViewModel,
                onNavigateToReport = { navController.navigate("report") },
                onNavigateToReportsList = { navController.navigate("reports-list") },
                onNavigateToEmergency = { navController.navigate("emergency") },
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToNotifications = { navController.navigate("notifications") }
            )
        }

        // Reports List
        composable("reports-list") {
            ReportsListScreen(
                mapViewModel = mapViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetail = { reportId ->
                    navController.navigate("report-detail/$reportId")
                }
            )
        }

        // Report Detail
        composable("report-detail/{reportId}") { backStackEntry ->
            val reportId = backStackEntry.arguments?.getString("reportId") ?: ""
            ReportDetailScreen(
                reportId = reportId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // New Report (Multi-step)
        composable("report") {
            ReportScreen(
                onNavigateBack = { navController.popBackStack() },
                onReportSubmitted = {
                    navController.popBackStack()
                }
            )
        }

        // Emergency
        composable("emergency") {
            EmergencyScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Notifications
        composable("notifications") {
            NotificationsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Profile
        composable("profile") {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("profile") { inclusive = true }
                    }
                }
            )
        }

        // Settings
        composable("settings") {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
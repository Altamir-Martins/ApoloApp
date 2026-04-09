package com.apolo.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var proximityAlertsEnabled by remember { mutableStateOf(true) }
    var statusUpdatesEnabled by remember { mutableStateOf(false) }
    var anonymousModeEnabled by remember { mutableStateOf(true) }
    var shareLocationEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var largeTextEnabled by remember { mutableStateOf(false) }
    var highContrastEnabled by remember { mutableStateOf(false) }
    var voiceReadingEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                "Configurações",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(40.dp))
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Notifications Section
            SettingsSection(title = "Notificações") {
                ToggleSetting(
                    label = "Alertas de área",
                    enabled = notificationsEnabled,
                    onToggle = { notificationsEnabled = it }
                )
                ToggleSetting(
                    label = "Denúncias próximas",
                    enabled = proximityAlertsEnabled,
                    onToggle = { proximityAlertsEnabled = it }
                )
                ToggleSetting(
                    label = "Atualizações de status",
                    enabled = statusUpdatesEnabled,
                    onToggle = { statusUpdatesEnabled = it }
                )
            }

            // Privacy Section
            SettingsSection(title = "Privacidade") {
                ToggleSetting(
                    label = "Modo Anônimo",
                    enabled = anonymousModeEnabled,
                    onToggle = { anonymousModeEnabled = it }
                )
                ToggleSetting(
                    label = "Compartilhar localização",
                    enabled = shareLocationEnabled,
                    onToggle = { shareLocationEnabled = it }
                )
            }

            // Appearance Section
            SettingsSection(title = "Aparência") {
                ToggleSetting(
                    label = "Modo Escuro",
                    enabled = darkModeEnabled,
                    onToggle = { darkModeEnabled = it }
                )
            }

            // Accessibility Section
            SettingsSection(title = "Acessibilidade") {
                ToggleSetting(
                    label = "Texto grande",
                    enabled = largeTextEnabled,
                    onToggle = { largeTextEnabled = it }
                )
                ToggleSetting(
                    label = "Alto contraste",
                    enabled = highContrastEnabled,
                    onToggle = { highContrastEnabled = it }
                )
                ToggleSetting(
                    label = "Leitura de voz",
                    enabled = voiceReadingEnabled,
                    onToggle = { voiceReadingEnabled = it }
                )
            }

            // Version
            Text(
                "Versão 1.0.0 — APOLO Segurança Comunitária",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        content()
        Divider(modifier = Modifier.padding(top = 12.dp))
    }
}

@Composable
fun ToggleSetting(
    label: String,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 14.sp)
        Switch(
            checked = enabled,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF667eea),
                checkedTrackColor = Color(0xFF667eea).copy(alpha = 0.5f)
            )
        )
    }
}
package com.apolo.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToLogout: () -> Unit
) {
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
                "Meu Perfil",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onNavigateToSettings) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings")
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFF667eea), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("👤", fontSize = 40.sp)
            }

            IconButton(
                onClick = { },
                modifier = Modifier
                    .offset(x = 30.dp, y = (-12).dp)
                    .size(36.dp)
                    .background(Color(0xFF667eea), shape = CircleShape)
            ) {
                Icon(Icons.Filled.CameraAlt, contentDescription = "Change avatar", tint = Color.White)
            }

            // User info
            Text(
                "Usuário Anônimo",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )

            Text(
                "Membro desde Jan 2025",
                fontSize = 12.sp,
                color = Color.Gray
            )

            // Stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(number = "7", label = "Denúncias", modifier = Modifier.weight(1f))
                StatCard(number = "3", label = "Validações", modifier = Modifier.weight(1f))
                StatCard(number = "12", label = "Alertas", modifier = Modifier.weight(1f))
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Menu
            ProfileMenuItem(
                icon = Icons.Filled.List,
                label = "Minhas Denúncias",
                onClick = { }
            )
            ProfileMenuItem(
                icon = Icons.Filled.Settings,
                label = "Configurações",
                onClick = onNavigateToSettings
            )
            ProfileMenuItem(
                icon = Icons.Filled.Help,
                label = "Ajuda",
                onClick = { }
            )
            ProfileMenuItem(
                icon = Icons.Filled.Logout,
                label = "Encerrar Sessão",
                onClick = onNavigateToLogout,
                isDanger = true
            )
        }
    }
}

@Composable
fun StatCard(
    number: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(number, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(label, fontSize = 10.sp, color = Color.Gray, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    isDanger: Boolean = false
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    icon,
                    contentDescription = label,
                    tint = if (isDanger) Color(0xFFE53935) else Color.Black
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    label,
                    fontSize = 14.sp,
                    color = if (isDanger) Color(0xFFE53935) else Color.Black
                )
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = "", tint = Color.Gray)
        }
    }
}
package com.apolo.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmergencyScreen(
    onNavigateBack: () -> Unit
) {
    var emergencyActive by remember { mutableStateOf(false) }
    var holdProgress by remember { mutableStateOf(0f) }
    val infiniteTransition = rememberInfiniteTransition(label = "emergency_pulse")

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
                "Emergência",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(40.dp))
        }

        if (!emergencyActive) {
            // Idle state
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "🚨",
                    fontSize = 64.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Text(
                    "Acionar Emergência",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Text(
                    "Pressione e segure o botão abaixo por 3 segundos",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 40.dp)
                )

                // Hold button
                Button(
                    onClick = { emergencyActive = true },
                    modifier = Modifier
                        .size(120.dp),
                    shape = androidx.compose.foundation.shape.CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                ) {
                    Text("🚨", fontSize = 48.sp)
                }

                Text(
                    "Segure",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 12.dp),
                    color = Color.Gray
                )

                // Emergency contacts
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                ) {
                    Text(
                        "Contatos de Emergência",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        EmergencyContactCard("Polícia", "190", Color(0xFF1E88E5), modifier = Modifier.weight(1f))
                        EmergencyContactCard("SAMU", "192", Color(0xFFE53935), modifier = Modifier.weight(1f))
                        EmergencyContactCard("Bombeiros", "193", Color(0xFFFB8C00), modifier = Modifier.weight(1f))
                    }
                }
            }
        } else {
            // Active state
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Pulsing icon
                val scale by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 1.2f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 600),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "pulse"
                )

                Text(
                    "🚨",
                    fontSize = 80.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Text(
                    "Emergência Acionada!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE53935),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Text(
                    "Serviços de emergência notificados. Aguarde.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 40.dp)
                )

                // Protocol number
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Protocolo", fontSize = 12.sp, color = Color.Gray)
                        Text(
                            "#APL-${System.currentTimeMillis() % 1000000}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Cancel button
                Button(
                    onClick = { emergencyActive = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color.Red)
                ) {
                    Text("Cancelar Emergência", color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun EmergencyContactCard(
    label: String,
    number: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(number, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}
package com.apolo.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotificationsScreen(
    onNavigateBack: () -> Unit
) {
    val notifications = listOf(
        Notification(
            id = "1",
            title = "Alerta de Área",
            message = "Roubo registrado a 500m de você",
            timestamp = "há 5 min",
            type = "alert",
            read = false
        ),
        Notification(
            id = "2",
            title = "Denúncia Validada",
            message = "Sua denúncia recebeu 5 validações",
            timestamp = "há 2h",
            type = "validation",
            read = true
        ),
        Notification(
            id = "3",
            title = "Atualização de Rota",
            message = "Rota alternativa disponível",
            timestamp = "há 1 dia",
            type = "info",
            read = true
        )
    )

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
                "Notificações",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Done, contentDescription = "Mark as read")
            }
        }

        // Notifications list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notifications) { notification ->
                NotificationCard(notification)
            }
        }
    }
}

@Composable
fun NotificationCard(notification: Notification) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = if (notification.read) Color(0xFFFAFAFA) else Color(0xFFE3F2FD),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = when (notification.type) {
                            "alert" -> Color(0xFFE53935).copy(alpha = 0.2f)
                            "validation" -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                            else -> Color(0xFF667eea).copy(alpha = 0.2f)
                        },
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    when (notification.type) {
                        "alert" -> "⚠️"
                        "validation" -> "✓"
                        else -> "ℹ️"
                    },
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    notification.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    notification.message,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    notification.timestamp,
                    fontSize = 10.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            if (!notification.read) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = Color(0xFF667eea),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }
}

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val type: String,
    val read: Boolean
)
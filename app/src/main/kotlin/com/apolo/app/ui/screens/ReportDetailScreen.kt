package com.apolo.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReportDetailScreen(
    reportId: String,
    onNavigateBack: () -> Unit
) {
    // Mock report data
    val report = com.apolo.app.viewmodel.Report(
        id = reportId,
        type = "Roubo/Furto",
        description = "Assalto na avenida principal. Homem de camiseta preta levou a bolsa de uma senhora.",
        latitude = -23.5608,
        longitude = -46.6560,
        riskLevel = "Alto",
        timestamp = System.currentTimeMillis(),
        witnesses = 5
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
                "Detalhe",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Share, contentDescription = "Share")
            }
        }

        // Content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Type and Risk
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    report.type,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                RiskBadge(report.riskLevel)
            }

            // Location
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xF5F5F5))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("📍 Localização", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text(
                        "${report.latitude}, ${report.longitude}",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // Description
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xF5F5F5))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Descrição", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text(
                        report.description,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // Witnesses
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xF5F5F5))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Testemunhas", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text(
                        "${report.witnesses} pessoas confirmaram",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // Anonymous info
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                color = Color(0xFFE8EAF6),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🕵️", fontSize = 20.sp, modifier = Modifier.padding(end = 12.dp))
                    Text(
                        "Denúncia anônima - Identidade protegida",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        // Action button
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF667eea))
        ) {
            Text("Validar esta denúncia", color = Color.White)
        }
    }
}
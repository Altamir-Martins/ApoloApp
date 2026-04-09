package com.apolo.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.rememberLazyListState
import com.apolo.app.viewmodel.MapViewModel

@Composable
fun ReportsListScreen(
    mapViewModel: MapViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    val reports = mapViewModel.reports.collectAsState().value
    var selectedTab by remember { mutableStateOf("all") }

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
                "Ocorrências",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { }) {
                Icon(Icons.Filled.FilterList, contentDescription = "Filter")
            }
        }

        // Tabs
        TabRow(
            selectedTabIndex = listOf("all", "near", "recent").indexOf(selectedTab),
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedTab == "all",
                onClick = { selectedTab = "all" },
                text = { Text("Todas", fontSize = 12.sp) }
            )
            Tab(
                selected = selectedTab == "near",
                onClick = { selectedTab = "near" },
                text = { Text("Próximas", fontSize = 12.sp) }
            )
            Tab(
                selected = selectedTab == "recent",
                onClick = { selectedTab = "recent" },
                text = { Text("Recentes", fontSize = 12.sp) }
            )
        }

        // Reports List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = rememberLazyListState()
        ) {
            items(reports) { report ->
                ReportCard(
                    report = report,
                    onClick = { onNavigateToDetail(report.id) }
                )
            }
        }
    }
}

@Composable
fun ReportCard(
    report: com.apolo.app.viewmodel.Report,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    report.type,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                RiskBadge(report.riskLevel)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                report.description,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "📍 ${report.latitude.toString().take(8)}, ${report.longitude.toString().take(8)}",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Text(
                    "👥 ${report.witnesses} testemunhas",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun RiskBadge(riskLevel: String) {
    val color = when (riskLevel) {
        "Baixo" -> Color(0xFF4CAF50)
        "Médio" -> Color(0xFFFFEB3B)
        "Alto" -> Color(0xFFFF9800)
        "Crítico" -> Color(0xFFF44336)
        else -> Color.Gray
    }

    Surface(
        modifier = Modifier.padding(2.dp),
        color = color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            riskLevel,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
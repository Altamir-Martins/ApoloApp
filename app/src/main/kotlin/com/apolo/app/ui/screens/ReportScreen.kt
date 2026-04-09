package com.apolo.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apolo.app.viewmodel.MapViewModel
import com.apolo.app.viewmodel.NewReport

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReportScreen(
    onNavigateBack: () -> Unit,
    onReportSubmitted: () -> Unit
) {
    var currentStep by remember { mutableStateOf(1) }
    var selectedIncidentType by remember { mutableStateOf<String?>(null) }
    var selectedRiskLevel by remember { mutableStateOf("Baixo") }
    var description by remember { mutableStateOf("") }
    var isWitness by remember { mutableStateOf(false) }

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
                "Nova Denúncia",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(40.dp))
        }

        // Steps indicator
        StepsBar(currentStep = currentStep)

        // Step content
        when (currentStep) {
            1 -> ReportStep1(
                selectedType = selectedIncidentType,
                onTypeSelected = { selectedIncidentType = it }
            )
            2 -> ReportStep2(
                riskLevel = selectedRiskLevel,
                description = description,
                onRiskChange = { selectedRiskLevel = it },
                onDescriptionChange = { description = it }
            )
            3 -> ReportStep3(
                incidentType = selectedIncidentType ?: "Desconhecido",
                riskLevel = selectedRiskLevel,
                description = description,
                isWitness = isWitness,
                onWitnessChange = { isWitness = it }
            )
        }

        // Navigation buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (currentStep > 1) {
                Button(
                    onClick = { currentStep-- },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray)
                ) {
                    Text("Voltar", color = Color.Black)
                }
            }

            Button(
                onClick = {
                    if (currentStep < 3) {
                        currentStep++
                    } else {
                        onReportSubmitted()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF667eea)),
                enabled = selectedIncidentType != null
            ) {
                Text(if (currentStep < 3) "Próximo" else "Enviar Denúncia", color = Color.White)
            }
        }
    }
}

@Composable
fun StepsBar(currentStep: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { step ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(
                        color = if (step + 1 <= currentStep) Color(0xFF667eea) else Color.LightGray,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    (step + 1).toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ColumnScope.ReportStep1(
    selectedType: String?,
    onTypeSelected: (String) -> Unit
) {
    val incidentTypes = listOf(
        Triple("🎭", "Roubo/Furto", "crime"),
        Triple("💀", "Tráfico", "crime"),
        Triple("🚗", "Acidente", "accident"),
        Triple("💡", "Iluminação", "infrastructure"),
        Triple("🛣️", "Via Perigosa", "infrastructure"),
        Triple("👊", "Violência", "violence"),
        Triple("🕵️", "Suspeito", "suspect"),
        Triple("❓", "Outro", "other")
    )

    Column(
        modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            "Selecione o tipo de ocorrência:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(incidentTypes) { (icon, type, _) ->
                IncidentCard(
                    icon = icon,
                    label = type,
                    isSelected = selectedType == type,
                    onClick = { onTypeSelected(type) }
                )
            }
        }
    }
}

@Composable
fun IncidentCard(
    icon: String,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() },
        color = if (isSelected) Color(0xFF667eea).copy(alpha = 0.1f) else Color(0xFFF5F5F5),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF667eea)) else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(icon, fontSize = 32.sp, modifier = Modifier.padding(bottom = 8.dp))
            Text(label, fontSize = 12.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.ReportStep2(
    riskLevel: String,
    description: String,
    onRiskChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Risk level
        Text("Nível de risco:", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(bottom = 12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Baixo", "Médio", "Alto", "Crítico").forEach { risk ->
                FilterChip(
                    selected = riskLevel == risk,
                    onClick = { onRiskChange(risk) },
                    label = { Text(risk, fontSize = 12.sp) }
                )
            }
        }

        // Description
        Text("Descrição:", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(bottom = 12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .padding(bottom = 16.dp),
            placeholder = { Text("Descreva o que está acontecendo...") },
            maxLines = 5
        )

        // Location
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { },
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("📍 Usar localização atual", fontSize = 14.sp, modifier = Modifier.weight(1f))
                Text(">", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ColumnScope.ReportStep3(
    incidentType: String,
    riskLevel: String,
    description: String,
    isWitness: Boolean,
    onWitnessChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Summary
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row {
                    Text("Tipo:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text(incidentType)
                }
                Row {
                    Text("Risco:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text(riskLevel)
                }
            }
        }

        // Anonymous info
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            color = Color(0xFFE8EAF6),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🕵️", fontSize = 24.sp, modifier = Modifier.padding(end = 12.dp))
                Text(
                    "Sua identidade está protegida. Esta denúncia será 100% anônima.",
                    fontSize = 12.sp
                )
            }
        }

        // Witness question
        Text("Você testemunhou diretamente o fato?", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(bottom = 12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onWitnessChange(true) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isWitness) Color(0xFF667eea) else Color.LightGray
                )
            ) {
                Text("✓ Sim, testemunhei", color = Color.White)
            }
            Button(
                onClick = { onWitnessChange(false) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isWitness) Color(0xFF667eea) else Color.LightGray
                )
            ) {
                Text("✗ Não testemunhei", color = Color.White)
            }
        }
    }
}
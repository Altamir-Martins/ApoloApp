package com.apolo.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onComplete: () -> Unit) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val scope = rememberCoroutineScope()

    val slides = listOf(
        OnboardingSlide(
            icon = "🛡️",
            title = "Segurança Anônima",
            description = "Denuncie crimes e situações de risco sem revelar sua identidade."
        ),
        OnboardingSlide(
            icon = "📍",
            title = "Mapa em Tempo Real",
            description = "Veja as zonas de risco ao seu redor e tome decisões mais seguras."
        ),
        OnboardingSlide(
            icon = "🚨",
            title = "Emergência Imediata",
            description = "Um botão de emergência conecta você rapidamente aos serviços de socorro."
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingSlideContent(slides[page])
        }

        // Dots
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = if (index == pagerState.currentPage) Color(0xFF667eea) else Color.LightGray,
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                        .padding(horizontal = 4.dp)
                )
            }
        }

        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onComplete,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color.LightGray)
            ) {
                Text("Pular", color = Color.Black)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {
                    if (pagerState.currentPage < 2) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onComplete()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF667eea))
            ) {
                Text("Próximo", color = Color.White)
            }
        }
    }
}

@Composable
fun OnboardingSlideContent(slide: OnboardingSlide) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            slide.icon,
            fontSize = 80.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Text(
            slide.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            slide.description,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

data class OnboardingSlide(
    val icon: String,
    val title: String,
    val description: String
)
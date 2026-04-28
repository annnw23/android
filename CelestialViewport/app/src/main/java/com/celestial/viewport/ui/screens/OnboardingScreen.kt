package com.celestial.viewport.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.celestial.viewport.ui.components.CosmicButton
import com.celestial.viewport.ui.components.GlassCard
import com.celestial.viewport.ui.theme.*

@Composable
fun OnboardingScreen(onStartMission: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.15f, targetValue = 0.35f, label = "glow",
        animationSpec = infiniteRepeatable(tween(2000), RepeatMode.Reverse)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Nebula glow background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(Primary.copy(alpha = glowAlpha), Color.Transparent),
                        radius = 600f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo orb
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .border(1.dp, Primary.copy(alpha = 0.3f), CircleShape)
                    .background(GlassPanel),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.RocketLaunch,
                    contentDescription = "Celestial Viewport",
                    tint = Primary,
                    modifier = Modifier.size(56.dp)
                )
            }

            Spacer(Modifier.height(32.dp))

            // Headline
            Text(
                text = buildAnnotatedString {
                    append("Welcome, ")
                    withStyle(SpanStyle(
                        brush = Brush.horizontalGradient(listOf(Primary, Secondary)),
                        fontWeight = FontWeight.Bold
                    )) { append("Explorer!") }
                },
                style = MaterialTheme.typography.displayMedium.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                ),
                color = OnSurface
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Your journey through the cosmos begins here. Access the Starship Glass interface to begin your mission.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light,
                    lineHeight = 26.sp,
                    color = OnSurfaceVariant
                )
            )

            Spacer(Modifier.height(40.dp))

            // Feature bento grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FeatureCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.MenuBook,
                    title = "Learn",
                    desc = "Master celestial mechanics of the deep void.",
                    color = Primary
                )
                FeatureCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Explore,
                    title = "Explore",
                    desc = "Navigate uncharted sectors.",
                    color = Secondary
                )
                FeatureCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.MilitaryTech,
                    title = "Conquer",
                    desc = "Rise through galactic ranks.",
                    color = Tertiary
                )
            }

            Spacer(Modifier.height(48.dp))

            CosmicButton(
                text = "Start Mission",
                onClick = onStartMission,
                leadingIcon = Icons.Default.ChevronRight,
                modifier = Modifier.widthIn(max = 320.dp)
            )

            Spacer(Modifier.height(16.dp))
            Text(
                text = "READY FOR IGNITION • V4.2.0",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariant.copy(alpha = 0.6f),
                    letterSpacing = 2.sp
                )
            )
        }

        // Phase indicator in header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Text(
                text = "PHASE 01",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariant,
                    letterSpacing = 2.sp
                )
            )
        }
    }
}

@Composable
private fun FeatureCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    desc: String,
    color: Color
) {
    GlassCard(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
            color = OnSurface
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = desc,
            style = MaterialTheme.typography.bodySmall.copy(lineHeight = 16.sp),
            color = OnSurfaceVariant
        )
    }
}

package com.celestial.viewport.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.celestial.viewport.ui.components.CosmicButton
import com.celestial.viewport.ui.theme.*

@Composable
fun QuizResultScreen(
    score: Int,         // number correct
    total: Int,
    onRetry: () -> Unit,
    onDone: () -> Unit
) {
    val percent = if (total == 0) 0 else (score * 100 / total)
    val passed = percent >= 60
    val color = if (passed) Primary else Error

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f, targetValue = 1.05f, label = "scale",
        animationSpec = infiniteRepeatable(tween(1200), RepeatMode.Reverse)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Score ring
        Box(
            modifier = Modifier
                .size(180.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(color.copy(alpha = 0.15f), Background)
                    )
                )
                .clip(CircleShape)
                .background(GlassPanel),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = if (passed) Icons.Default.EmojiEvents else Icons.Default.Refresh,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "$percent%",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Black,
                        color = color
                    )
                )
                Text(
                    text = "$score / $total",
                    style = MaterialTheme.typography.bodySmall.copy(color = OnSurfaceVariant)
                )
            }
        }

        Spacer(Modifier.height(40.dp))

        Text(
            text = if (passed) "Mission Complete!" else "Keep Exploring",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = OnSurface,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = if (passed)
                "Outstanding work! You've earned the badge and XP reward. The cosmos applauds you."
            else
                "Not quite passing score. Review the lesson and try again to earn full XP.",
            style = MaterialTheme.typography.bodyMedium.copy(
                lineHeight = 22.sp,
                color = OnSurfaceVariant,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.widthIn(max = 320.dp)
        )

        Spacer(Modifier.height(16.dp))

        // XP earned chip
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(color.copy(alpha = 0.1f))
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Text(
                text = "+ ${if (passed) "500 XP" else "125 XP"} EARNED",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = color,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            )
        }

        Spacer(Modifier.height(48.dp))

        CosmicButton(
            text = "Return to Missions",
            onClick = onDone,
            leadingIcon = Icons.Default.Dashboard,
            modifier = Modifier.widthIn(max = 320.dp)
        )

        if (!passed) {
            Spacer(Modifier.height(12.dp))
            OutlinedButton(
                onClick = onRetry,
                modifier = Modifier
                    .widthIn(max = 320.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurfaceVariant)
            ) {
                Icon(Icons.Default.Refresh, null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(
                    "RETRY QUIZ",
                    style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 1.5.sp)
                )
            }
        }
    }
}

package com.celestial.viewport.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.celestial.viewport.ui.components.CosmicButton
import com.celestial.viewport.ui.components.GlassCard
import com.celestial.viewport.ui.theme.*
import com.celestial.viewport.viewmodel.LessonDetailViewModel

@Composable
fun LessonDetailScreen(
    onBack: () -> Unit,
    onTakeQuiz: (Int) -> Unit,
    viewModel: LessonDetailViewModel = hiltViewModel()
) {
    val lesson by viewModel.lesson.collectAsState()
    val quiz by viewModel.quiz.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Hero image
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
            ) {
                AsyncImage(
                    model = lesson?.imageUrl,
                    contentDescription = lesson?.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Background.copy(alpha = 0.2f),
                                    Background.copy(alpha = 0.9f)
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )
                // Back button
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(50))
                        .background(SurfaceContainerHigh.copy(alpha = 0.6f))
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Primary)
                }

                // Title overlay
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(24.dp)
                ) {
                    lesson?.let { l ->
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = SurfaceBright,
                            modifier = Modifier.padding(bottom = 12.dp)
                        ) {
                            Text(
                                text = "MISSION #${l.missionNumber.toString().padStart(3, '0')}",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Primary,
                                    letterSpacing = 2.sp
                                )
                            )
                        }
                        Text(
                            text = l.title,
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.Bold,
                                lineHeight = 48.sp
                            ),
                            color = OnSurface
                        )
                        Text(
                            text = l.description,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Light,
                                lineHeight = 26.sp,
                                color = OnSurfaceVariant
                            )
                        )
                    }
                }
            }
        }

        // Content grid
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Sidebar metadata card
                GlassCard(
                    modifier = Modifier
                        .weight(0.45f)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Text(
                        "ORBITAL METADATA",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Primary,
                            letterSpacing = 2.sp
                        )
                    )
                    Spacer(Modifier.height(16.dp))
                    MetaStatRow("Thickness", "~10 Meters")
                    Spacer(Modifier.height(12.dp))
                    MetaStatRow("Composition", "99.9% Water Ice")
                    Spacer(Modifier.height(12.dp))
                    MetaStatRow("Primary Rings", "7 Distinct Bands")
                }

                // Main article text
                Column(modifier = Modifier.weight(0.55f)) {
                    Text(
                        text = "Saturn's rings are not solid sheets. They are composed of billions of individual particles, ranging from microscopic dust to mountain-sized boulders, zipping around the planet at tens of thousands of miles per hour.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Light,
                            lineHeight = 26.sp
                        ),
                        color = OnSurface
                    )
                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider(
                        modifier = Modifier.width(64.dp),
                        color = Primary.copy(alpha = 0.3f)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "The Roche Limit",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = Primary
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            append("The rings exist because of a delicate balance between gravity and velocity. Any moon orbiting within Saturn's ")
                            withStyle(SpanStyle(color = Secondary, fontWeight = FontWeight.SemiBold)) {
                                append("Roche Limit")
                            }
                            append(" is pulverized by the planet's gravitational pull, preventing fragments from coalescing.")
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                        color = OnSurfaceVariant
                    )
                }
            }
        }

        // Cassini insight callout
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Tertiary, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            "CASSINI INSIGHT",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Tertiary,
                                letterSpacing = 2.sp
                            )
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "The rings are far younger than the planet itself, potentially formed when a moon strayed too close and was torn apart by tidal forces.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                lineHeight = 18.sp
                            ),
                            color = OnSurfaceVariant
                        )
                    }
                }
            }
        }

        // Quiz CTA
        item {
            Spacer(Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(listOf(Primary, Secondary, Tertiary))
                    )
                    .padding(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(SurfaceDim)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.AssignmentTurnedIn,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Mastered the Rings?",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnSurface
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Test your knowledge of orbital mechanics and ring composition to earn the '${quiz?.title ?: "Saturnian Navigator"}' badge.",
                        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                        color = OnSurfaceVariant
                    )
                    Spacer(Modifier.height(24.dp))
                    quiz?.let { q ->
                        CosmicButton(
                            text = "Take the Quiz",
                            onClick = { onTakeQuiz(q.id) },
                            leadingIcon = Icons.Default.ArrowForward,
                            modifier = Modifier.widthIn(max = 280.dp)
                        )
                    } ?: run {
                        Text(
                            "No quiz available yet",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MetaStatRow(label: String, value: String) {
    Column {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                color = OnSurfaceVariant,
                letterSpacing = 1.5.sp
            )
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            color = OnSurface
        )
    }
}

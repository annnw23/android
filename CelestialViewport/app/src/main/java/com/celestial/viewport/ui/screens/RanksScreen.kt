package com.celestial.viewport.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.celestial.viewport.data.entity.User
import com.celestial.viewport.ui.components.CosmicButton
import com.celestial.viewport.ui.theme.*
import com.celestial.viewport.viewmodel.RanksViewModel

@Composable
fun RanksScreen(viewModel: RanksViewModel = hiltViewModel()) {
    val users by viewModel.rankedUsers.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    val top3 = users.take(3)
    val rest = users.drop(3)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {

        // ─── Top 3 Podium ──────────────────────────────────────────────────
        item {
            Spacer(Modifier.height(16.dp).statusBarsPadding())
            if (top3.size >= 3) {
                PodiumSection(gold = top3[0], silver = top3[1], bronze = top3[2])
            }
        }

        // ─── Section header ─────────────────────────────────────────────────
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "SECTOR RANKS",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = OnSurfaceVariant,
                        letterSpacing = 2.sp
                    )
                )
                Surface(
                    shape = CircleShape,
                    color = SurfaceContainerHighest
                ) {
                    Text(
                        "All Time",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Secondary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // ─── Leaderboard list ───────────────────────────────────────────────
        itemsIndexed(rest) { index, user ->
            val position = index + 4
            val isCurrentUser = user.id == currentUser?.id
            LeaderboardRow(
                position = position,
                user = user,
                isCurrentUser = isCurrentUser,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
            )
        }

        // ─── Challenge button ────────────────────────────────────────────────
        item {
            Spacer(Modifier.height(24.dp))
            CosmicButton(
                text = "Challenge a Friend",
                onClick = {},
                leadingIcon = Icons.Default.Bolt,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
            Text(
                "RANK UPDATES EVERY 24 SOLAR HOURS",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariant,
                    letterSpacing = 1.5.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
        }
    }
}

@Composable
private fun PodiumSection(gold: User, silver: User, bronze: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(260.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        // Silver – #2
        PodiumItem(
            user = silver,
            position = 2,
            ringColor = Color(0xFF9E9E9E),
            labelColor = OnSurfaceVariant,
            label = "SILVER",
            avatarSize = 64.dp,
            columnHeight = 0.75f,
            modifier = Modifier.weight(1f)
        )
        // Gold – #1 (center, larger)
        PodiumItem(
            user = gold,
            position = 1,
            ringColor = Tertiary,
            labelColor = Tertiary,
            label = "COMMANDER",
            avatarSize = 80.dp,
            columnHeight = 1f,
            isFirst = true,
            modifier = Modifier.weight(1.2f)
        )
        // Bronze – #3
        PodiumItem(
            user = bronze,
            position = 3,
            ringColor = TertiaryDim,
            labelColor = OnSurfaceVariant,
            label = "BRONZE",
            avatarSize = 64.dp,
            columnHeight = 0.6f,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun PodiumItem(
    user: User,
    position: Int,
    ringColor: Color,
    labelColor: Color,
    label: String,
    avatarSize: androidx.compose.ui.unit.Dp,
    columnHeight: Float,
    isFirst: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Crown icon for #1
        if (isFirst) {
            Icon(
                Icons.Default.WorkspacePremium,
                contentDescription = null,
                tint = Tertiary,
                modifier = Modifier.size(24.dp)
            )
        }

        // Avatar circle
        Box(
            modifier = Modifier
                .size(avatarSize + 4.dp)
                .clip(CircleShape)
                .background(ringColor),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(avatarSize)
                    .clip(CircleShape)
                    .background(SurfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = user.username,
                    tint = ringColor,
                    modifier = Modifier.size(avatarSize * 0.5f)
                )
            }
        }

        // Rank number badge
        Box(
            modifier = Modifier
                .offset(y = (-12).dp)
                .size(if (isFirst) 32.dp else 24.dp)
                .clip(CircleShape)
                .background(ringColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = position.toString(),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnTertiaryFixed,
                    fontWeight = FontWeight.Black
                )
            )
        }

        // Podium card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(GlassPanel)
                .padding(horizontal = 8.dp, vertical = if (isFirst) 16.dp else 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = labelColor,
                    letterSpacing = 1.5.sp,
                    fontSize = if (isFirst) 10.sp else 8.sp
                )
            )
            Text(
                text = user.username,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = OnSurface
            )
            Text(
                text = "${user.xpTotal / 1000}.${(user.xpTotal % 1000) / 100}k XP",
                style = MaterialTheme.typography.labelSmall.copy(color = Primary, fontSize = 10.sp)
            )
        }
    }
}

@Composable
private fun LeaderboardRow(
    position: Int,
    user: User,
    isCurrentUser: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isCurrentUser) Primary.copy(alpha = 0.08f) else GlassPanel
            )
            .then(
                if (isCurrentUser)
                    Modifier // ring via border
                else Modifier
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Rank number
        Text(
            text = position.toString().padStart(2, '0'),
            modifier = Modifier.width(32.dp),
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = com.celestial.viewport.ui.theme.SpaceGrotesk,
                color = if (isCurrentUser) Primary else OnSurfaceVariant
            )
        )

        // Avatar
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (isCurrentUser) Primary.copy(alpha = 0.15f) else SurfaceContainerHigh
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = if (isCurrentUser) Primary else OnSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(Modifier.width(12.dp))

        // Name & rank
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isCurrentUser) Primary else OnSurface
                    )
                )
                if (isCurrentUser) {
                    Spacer(Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Primary
                    ) {
                        Text(
                            "YOU",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnPrimaryContainer,
                                fontWeight = FontWeight.Black,
                                fontSize = 8.sp,
                                letterSpacing = 1.sp
                            ),
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            Text(
                text = user.rank.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = if (isCurrentUser) Primary.copy(alpha = 0.7f) else OnSurfaceVariant,
                    letterSpacing = 1.5.sp
                )
            )
        }

        // XP
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${user.xpTotal / 1000},${(user.xpTotal % 1000).toString().padStart(3, '0')}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Primary,
                    fontFamily = com.celestial.viewport.ui.theme.SpaceGrotesk
                )
            )
            Text(
                "XP TOTAL",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = if (isCurrentUser) Primary.copy(0.6f) else OnSurfaceVariant,
                    fontSize = 8.sp,
                    letterSpacing = 1.sp
                )
            )
        }
    }
}

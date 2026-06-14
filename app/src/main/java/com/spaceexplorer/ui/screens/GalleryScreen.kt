package com.spaceexplorer.ui.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.spaceexplorer.ui.theme.*
import com.spaceexplorer.ui.viewmodel.GalleryViewModel
import kotlin.OptIn

private const val NASA_USER_AGENT = "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Mobile Safari/537.36"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    navController: NavController,
    viewModel: GalleryViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(uiState.imageUrls) {
        Log.d("GalleryScreen", "Images count in UI State: ${uiState.imageUrls.size}")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Galeria NASA", color = PrimaryText, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceColor)
            )
        },
        bottomBar = { SpaceBottomNav(navController) },
        containerColor = DarkBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Video Player Section
            VideoPlayerSection()

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Zdjęcia z archiwum NASA",
                style = MaterialTheme.typography.titleMedium,
                color = CyanAccent,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            if (uiState.imageUrls.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = CyanAccent)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Ładowanie galerii...", color = SecondaryText)
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    itemsIndexed(uiState.imageUrls) { index, url ->
                        var imageState by remember { mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty) }
                        
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(CardBackground)
                                .clickable { selectedImageUrl = url }
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(url)
                                    .addHeader("User-Agent", NASA_USER_AGENT)
                                    .crossfade(true)
                                    .allowHardware(false)
                                    .build(),
                                contentDescription = "NASA Image $index",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                onState = { imageState = it }
                            )
                            
                            when (imageState) {
                                is AsyncImagePainter.State.Loading -> {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp).align(Alignment.Center),
                                        color = CyanAccent,
                                        strokeWidth = 2.dp
                                    )
                                }
                                is AsyncImagePainter.State.Error -> {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.BrokenImage,
                                            contentDescription = "Error",
                                            tint = Color.Red.copy(alpha = 0.5f),
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Text("Błąd", color = Color.White.copy(alpha = 0.5f), style = MaterialTheme.typography.labelSmall)
                                    }
                                    Log.e("GalleryScreen", "Failed to load image $index: $url")
                                }
                                else -> {}
                            }
                        }
                    }
                }
            }
        }
    }

    // Full Screen Image Dialog
    selectedImageUrl?.let { url ->
        Dialog(onDismissRequest = { selectedImageUrl = null }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(url)
                            .addHeader("User-Agent", NASA_USER_AGENT)
                            .allowHardware(false)
                            .build(),
                        contentDescription = "Full Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Fit
                    )
                    TextButton(onClick = { selectedImageUrl = null }) {
                        Text("Zamknij", color = CyanAccent)
                    }
                }
            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerSection() {
    val context = LocalContext.current
    val videoUrl = "https://images-assets.nasa.gov/video/301_BlackHoles/301_BlackHoles~orig.mp4"
    var errorState by remember { mutableStateOf<String?>(null) }

    val exoPlayer = remember {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent(NASA_USER_AGENT)
            .setAllowCrossProtocolRedirects(true)

        ExoPlayer.Builder(context.applicationContext)
            .setMediaSourceFactory(DefaultMediaSourceFactory(httpDataSourceFactory))
            .build().apply {
                val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = false 
                
                addListener(object : Player.Listener {
                    override fun onPlayerError(error: PlaybackException) {
                        Log.e("VideoPlayer", "ExoPlayer Error: ${error.message}", error)
                        errorState = "Błąd: ${error.errorCodeName}"
                    }
                })
            }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                Lifecycle.Event.ON_STOP -> exoPlayer.stop()
                Lifecycle.Event.ON_DESTROY -> exoPlayer.release()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        player = exoPlayer
                        useController = true
                        setBackgroundColor(android.graphics.Color.BLACK)
                    }
                },
                update = { playerView ->
                    playerView.player = exoPlayer
                },
                modifier = Modifier.fillMaxSize()
            )
            
            if (errorState != null) {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.CloudOff, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(errorState!!, color = Color.White, style = MaterialTheme.typography.bodySmall)
                        TextButton(onClick = { 
                            errorState = null
                            exoPlayer.prepare()
                            exoPlayer.play()
                        }) {
                            Text("Ponów", color = CyanAccent)
                        }
                    }
                }
            }
        }
    }
}

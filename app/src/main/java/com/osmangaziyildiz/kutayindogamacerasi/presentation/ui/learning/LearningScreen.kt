package com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.learning

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import com.osmangaziyildiz.kutayindogamacerasi.domain.model.LearningContent
import com.osmangaziyildiz.kutayindogamacerasi.util.AudioPlayer
import com.osmangaziyildiz.kutayindogamacerasi.util.LoadImageFromAssets
import com.osmangaziyildiz.kutayindogamacerasi.util.UiState
import kotlinx.coroutines.delay

@Composable
fun LearningScreen(
    navController: NavController,
    categoryId: String,
    viewModel: LearningViewModel,
    audioPlayer: AudioPlayer,
) {
    val contentsState by viewModel.contents.collectAsState()
    var currentIndex by remember { mutableIntStateOf(0) }
    val screenHeight = LocalConfiguration.current.screenHeightDp

    DisposableEffect(Unit) {
        onDispose {
            audioPlayer.stopAudio()
        }
    }

    LaunchedEffect(categoryId) {
        viewModel.fetchLearningContents(categoryId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        LoadImageFromAssets(
            imagePath = "images/wallpaper.png", modifier = Modifier.fillMaxSize()
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (contentsState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                is UiState.Error -> {
                    Text(
                        text = (contentsState as UiState.Error).message, color = Color.Black
                    )
                }

                is UiState.Success -> {
                    val contents =
                        (contentsState as UiState.Success<List<LearningContent>>).data
                    if (contents.isEmpty()) {
                        Text(
                            text = "İçerik bulunamadı", color = Color.Black
                        )
                        return@Column
                    }

                    val currentContent = contents[currentIndex]

                    // Content with animation
                    key(currentIndex) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize()
                        ) {
                            // Card - Fade In Animation
                            var visible by remember { mutableStateOf(false) }
                            LaunchedEffect(currentIndex) {
                                visible = false
                                delay(500)
                                visible = true
                            }

                            AnimatedVisibility(
                                visible = visible,
                                enter = fadeIn(
                                    animationSpec = tween(1500)
                                ) + expandVertically(
                                    animationSpec = tween(1500)
                                ),
                                modifier = Modifier
                                    .height(screenHeight * 0.40.dp)
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Card(
                                    shape = RoundedCornerShape(16.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                                ) {
                                    LoadImageFromAssets(
                                        imagePath = currentContent.imagePath,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }

                            // Description Card - Slide Animation
                            AnimatedVisibility(
                                visible = visible,
                                enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Card(
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = currentContent.description,
                                        color = Color.Black,
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(16.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            // Sound button
                            AnimatedVisibility(
                                visible = visible,
                                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Button(
                                    onClick = {
                                        audioPlayer.playAudio(currentContent.audioPath)
                                    }, colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White, contentColor = Color.Black
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Dinle",
                                        tint = Color.Black
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Dinle")
                                }
                            }
                        }
                    }

                    // Navigation buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { if (currentIndex > 0) currentIndex-- },
                            enabled = currentIndex > 0,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                                disabledContainerColor = Color.White.copy(alpha = 0.5f)
                            )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Önceki",
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Önceki")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { if (currentIndex < contents.size - 1) currentIndex++ },
                            enabled = currentIndex < contents.size - 1,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                                disabledContainerColor = Color.White.copy(alpha = 0.5f)
                            )
                        ) {
                            Text("Sonraki")
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Sonraki",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}
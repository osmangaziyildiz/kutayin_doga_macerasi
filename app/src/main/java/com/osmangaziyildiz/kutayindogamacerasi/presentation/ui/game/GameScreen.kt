package com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.game

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.osmangaziyildiz.kutayindogamacerasi.util.LoadImageFromAssets
import com.osmangaziyildiz.kutayindogamacerasi.util.UiState

@Composable
fun GameScreen(
    categoryId: String,
    navController: NavController,
    viewModel: GameViewModel,
) {
    val questionsState by viewModel.questions.collectAsState()
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchQuestions(categoryId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (questionsState) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is UiState.Error -> {
                Text(
                    text = (questionsState as UiState.Error).message,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            is UiState.Success -> {
                val currentQuestion = (questionsState as UiState.Success).data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = currentQuestion.questionTip,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    currentQuestion.options.forEach { option ->
                        val imagePath = getAssetPath(categoryId, option)
                        Button(
                            onClick = {
                                selectedOption = option
                                if (option == currentQuestion.correctOption) {
                                    if (viewModel.isLastQuestion()) {
                                        navController.popBackStack()
                                    } else {
                                        viewModel.moveToNextQuestion()
                                    }
                                } else {
                                    Toast.makeText(
                                        context, "Yanlış cevap!", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }, modifier = Modifier.fillMaxWidth()
                        ) {
                            LoadImageFromAssets(
                                imagePath = imagePath, modifier = Modifier.size(100.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

fun getAssetPath(categoryId: String, fileName: String): String {
    return "images/$categoryId/$fileName"
}
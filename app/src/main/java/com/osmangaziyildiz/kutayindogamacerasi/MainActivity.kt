package com.osmangaziyildiz.kutayindogamacerasi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.osmangaziyildiz.kutayindogamacerasi.presentation.navigation.AppNavHost
import com.osmangaziyildiz.kutayindogamacerasi.presentation.theme.KutayınDoğaMacerasıTheme
import com.osmangaziyildiz.kutayindogamacerasi.util.AudioPlayer
import com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.auth.AuthViewModel
import com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.home.HomeViewModel
import com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.learning.LearningViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val learningViewModel: LearningViewModel by viewModels()
    @Inject lateinit var audioPlayer: AudioPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KutayınDoğaMacerasıTheme {
                AppNavHost(
                    authViewModel = authViewModel,
                    homeViewModel = homeViewModel,
                    learningViewModel = learningViewModel,
                    audioPlayer = audioPlayer,
                )
            }
        }
    }
}

package com.osmangaziyildiz.kutayindogamacerasi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.auth.LoginScreen
import com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.auth.SignupScreen
import com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.home.HomeScreen
import com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.learning.LearningScreen
import com.osmangaziyildiz.kutayindogamacerasi.util.AudioPlayer
import com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.auth.AuthViewModel
import com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.home.HomeViewModel
import com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.learning.LearningViewModel

@Composable
fun AppNavHost(
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    learningViewModel: LearningViewModel,
    startDestination: String = Screen.Login.route,
    audioPlayer: AudioPlayer,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController, viewModel = authViewModel
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                navController = navController, viewModel = authViewModel
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController, viewModel = homeViewModel
            )
        }

        composable(Screen.Learning.route) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            if (categoryId != null) {
                LearningScreen(
                    navController = navController,
                    categoryId = categoryId,
                    viewModel = learningViewModel,
                    audioPlayer = audioPlayer,
                )
            }
        }
    }
}





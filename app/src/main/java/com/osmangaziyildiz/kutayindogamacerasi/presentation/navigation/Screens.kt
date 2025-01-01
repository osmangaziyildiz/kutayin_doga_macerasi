package com.osmangaziyildiz.kutayindogamacerasi.presentation.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Signup : Screen("signup")
    data object Home : Screen("home")

    data object Learning : Screen("learningScreen/{categoryId}") {

        fun createRoute(categoryId: String) = "learningScreen/$categoryId"
    }
}
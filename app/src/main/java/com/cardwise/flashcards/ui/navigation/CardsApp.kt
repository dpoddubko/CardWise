package com.cardwise.flashcards.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.cardwise.flashcards.ui.screen.AddCardScreen
import com.cardwise.flashcards.ui.screen.CardScreen
import com.cardwise.flashcards.ui.screen.LearningScreen

@Composable
fun CardsApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "cards") {
        composable("cards") {
            CardScreen(
                onNavigateToAddCard = { navController.navigate("addCard") },
                onNavigateToLearning = { navController.navigate("learning") }
            )
        }
        composable("addCard") {
            AddCardScreen(onCardAdded = { navController.popBackStack() })
        }
        composable("learning") {
            LearningScreen(
                languageId = 1,
                onExitLearning = { navController.popBackStack() }
            )
        }
    }
}
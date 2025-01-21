package com.sdc.aicookmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.sdc.aicookmate.ui.theme.AiCookMateTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(Modifier.safeDrawingPadding()) {
                AiCookMateTheme {
                    NavSys()
                }
            }
        }
    }
}

@Composable
fun NavSys() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable("main") {
            MainScreen(navController)
        }
        composable("myPage") {
            MyPageScreen(navController)
        }
        composable("shopping") {
            FoodShoppingScreen(navController)
        }
        composable("recipeExplain") {
            RecipeExplainScreen(navController)
        }
        composable("recipeScreen2") {
            RecipeScreen2(navController)
        }
        composable("Recipe") {
            RecipeScreen(navController)
        }
        composable("recipeList") {
            val viewModel: RecipeViewModel = viewModel()
            val recipes by viewModel.recipes.collectAsState()
            RecipeList(recipes = recipes, navController = navController)
        }

        composable("FirebaseExplainRecipe/{title}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            RecipeDetailScreen(title = title)
        }

    }
}
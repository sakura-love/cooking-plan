package com.cooking.plan.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cooking.plan.ui.favorites.FavoritesScreen
import com.cooking.plan.ui.guide.BeginnerGuideScreen
import com.cooking.plan.ui.home.HomeScreen
import com.cooking.plan.ui.plan.DailyShoppingListScreen
import com.cooking.plan.ui.plan.MealPlanDetailScreen
import com.cooking.plan.ui.plan.PlanScreen
import com.cooking.plan.ui.recipe.AddRecipeScreen
import com.cooking.plan.ui.recipe.RecipeDetailScreen
import com.cooking.plan.ui.settings.SettingsScreen
import com.cooking.plan.ui.shopping.ShoppingScreen

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Home : Screen("home", "首页", Icons.Default.Home)
    data object Plan : Screen("plan", "计划", Icons.AutoMirrored.Filled.List)
    data object Favorites : Screen("favorites", "收藏", Icons.Default.Favorite)
    data object Shopping : Screen("shopping", "采购", Icons.Default.ShoppingCart)
    data object Settings : Screen("settings", "设置", Icons.Default.Settings)
}

val bottomScreens = listOf(Screen.Home, Screen.Plan, Screen.Favorites, Screen.Settings)

@Composable
fun CookingPlanNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = currentDestination?.route in bottomScreens.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                Surface(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.8f))
                ) {
                    NavigationBar(
                        containerColor = Color.Transparent,
                        tonalElevation = 0.dp
                    ) {
                        bottomScreens.forEach { screen ->
                            NavigationBarItem(
                                icon = { Icon(screen.icon, contentDescription = screen.label) },
                                label = { Text(screen.label) },
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.88f),
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToRecipe = { id -> navController.navigate("recipe/$id") },
                    onNavigateToAddRecipe = { id ->
                        navController.navigate("add_recipe/${id ?: 0L}")
                    },
                    onNavigateToBeginnerGuide = { navController.navigate("beginner_guide") }
                )
            }
            composable(Screen.Plan.route) {
                PlanScreen(
                    onNavigateToMeal = { year, weekNumber, dayOfWeek, meal ->
                        navController.navigate("meal_plan/$year/$weekNumber/$dayOfWeek/$meal")
                    }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(onNavigateToRecipe = { id -> navController.navigate("recipe/$id") })
            }
            composable(Screen.Shopping.route) { ShoppingScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
            composable("beginner_guide") {
                BeginnerGuideScreen(onBack = { navController.popBackStack() })
            }

            composable(
                "meal_plan/{year}/{weekNumber}/{dayOfWeek}/{meal}",
                arguments = listOf(
                    navArgument("year") { type = NavType.IntType },
                    navArgument("weekNumber") { type = NavType.IntType },
                    navArgument("dayOfWeek") { type = NavType.IntType },
                    navArgument("meal") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val year = backStackEntry.arguments?.getInt("year") ?: 0
                val weekNumber = backStackEntry.arguments?.getInt("weekNumber") ?: 0
                val dayOfWeek = backStackEntry.arguments?.getInt("dayOfWeek") ?: 1
                val meal = backStackEntry.arguments?.getString("meal") ?: ""
                if (meal == "SHOPPING") {
                    DailyShoppingListScreen(
                        year = year,
                        weekNumber = weekNumber,
                        dayOfWeek = dayOfWeek,
                        onBack = { navController.popBackStack() }
                    )
                } else {
                    MealPlanDetailScreen(
                        year = year,
                        weekNumber = weekNumber,
                        dayOfWeek = dayOfWeek,
                        meal = meal,
                        onBack = { navController.popBackStack() },
                        onNavigateToRecipe = { id -> navController.navigate("recipe/$id") }
                    )
                }
            }

            composable(
                "recipe/{recipeId}",
                arguments = listOf(navArgument("recipeId") { type = NavType.LongType })
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getLong("recipeId") ?: 0L
                RecipeDetailScreen(recipeId = recipeId, onBack = { navController.popBackStack() })
            }

            composable(
                "add_recipe/{recipeId}",
                arguments = listOf(navArgument("recipeId") { type = NavType.LongType; defaultValue = 0L })
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getLong("recipeId") ?: 0L
                AddRecipeScreen(recipeId = if (recipeId > 0) recipeId else null, onBack = { navController.popBackStack() })
            }
        }
    }
}

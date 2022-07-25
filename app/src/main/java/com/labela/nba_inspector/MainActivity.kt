package com.labela.nba_inspector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.labela.nba_inspector.presentation.TeamDetailScreen
import com.labela.nba_inspector.presentation.TeamScreen
import com.labela.nba_inspector.ui.theme.NBAinspectorTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NBAinspectorTheme {
                NavHost(
                    navController = navController,
                    startDestination = "team"
                ) {
                    composable("team") { TeamScreen(navController = navController) }
                    composable(
                        "team_detail/{teamId}/{teamName}",
                        arguments = listOf(
                            navArgument("teamId") { type = NavType.StringType },
                            navArgument("teamName") { type = NavType.StringType },
                        )
                    ) { backStackEntry ->
                        TeamDetailScreen(
                            navController = navController,
                            teamId = backStackEntry.arguments?.getString("teamId") ?: "",
                            teamName = backStackEntry.arguments?.getString("teamName") ?: "",
                        )
                    }
                }
            }
        }
    }
}
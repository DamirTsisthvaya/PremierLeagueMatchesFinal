import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.compose.runtime.Composable
import com.example.premierleague.model.Match
import com.example.premierleague.ui.screens.matches.MatchViewModel
@Composable
fun PremierLeagueNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "matches"
    ) {
        composable("matches") {
            val viewModel: MatchViewModel = hiltViewModel()
            MatchesScreen(
                viewModel = viewModel,
                onMatchClick = { match: Match ->
                    navController.navigate("match_details/${match.MatchNumber}")
                }
            )
        }
        composable(
            "match_details/{matchId}",
            arguments = listOf(navArgument("matchId") { type = NavType.IntType })
        ) { backStackEntry ->
            MatchDetailsScreen(
                navController = navController,  // Передаем navController
                matchId = backStackEntry.arguments?.getInt("matchId") ?: 0
            )
        }
    }
}


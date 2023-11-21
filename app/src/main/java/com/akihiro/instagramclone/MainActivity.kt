package com.akihiro.instagramclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akihiro.instagramclone.common.Screens
import com.akihiro.instagramclone.presentation.SplashScreen
import com.akihiro.instagramclone.presentation.authentication.AuthenticationViewModel
import com.akihiro.instagramclone.presentation.authentication.LoginScreen
import com.akihiro.instagramclone.presentation.authentication.SignUpScreen
import com.akihiro.instagramclone.presentation.authentication.main.FeedScreen
import com.akihiro.instagramclone.presentation.authentication.main.ProfileScreen
import com.akihiro.instagramclone.presentation.authentication.main.SearchScreen
import com.akihiro.instagramclone.ui.theme.InstagramCloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val authViewModel: AuthenticationViewModel = hiltViewModel()
                    InstagramCloneApp(navController,authViewModel)
                }
            }
        }
    }
}

@Composable
fun InstagramCloneApp(navigator: NavHostController, authViewModel: AuthenticationViewModel) {
    NavHost(navController = navigator, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController = navigator, authViewModel)
        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navigator, authViewModel)
        }
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController = navigator, authViewModel = authViewModel)
        }

        composable(route = Screens.FeedScreen.route) {
            FeedScreen(navController = navigator)
        }

        composable(route = Screens.ProfileScreen.route) {
            ProfileScreen(navController = navigator)
        }

        composable(route = Screens.SearchScreen.route) {
            SearchScreen(navController = navigator)
        }
    }
}


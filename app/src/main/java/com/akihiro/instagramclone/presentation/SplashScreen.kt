package com.akihiro.instagramclone.presentation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.akihiro.instagramclone.R
import com.akihiro.instagramclone.common.Screens
import com.akihiro.instagramclone.presentation.authentication.AuthenticationViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, authViewModel: AuthenticationViewModel) {
    val authValue = authViewModel.isUserAuthenticated
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                })
        )
        delay(3000)
        if(authValue){
            navController.navigate(Screens.FeedScreen.route){
                popUpTo(Screens.SplashScreen.route){
                    inclusive = true
                }
            }
        }else{
            navController.navigate(Screens.LoginScreen.route){
                popUpTo(Screens.SplashScreen.route){
                    inclusive = true
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.scale(scale.value),
            painter = painterResource(id = R.drawable.instagram_logo),
            contentDescription = "Splash Screen Logo",
        )

    }
}
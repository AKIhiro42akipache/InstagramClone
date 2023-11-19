package com.akihiro.instagramclone.common

sealed class Screens(val route:String){
    object SplashScreen: Screens("splash_screen")
    object LoginScreen: Screens("login_screen")
    object SignUpScreen: Screens("sign_up_screen")
    object FeedScreen: Screens("feed_screen")
}

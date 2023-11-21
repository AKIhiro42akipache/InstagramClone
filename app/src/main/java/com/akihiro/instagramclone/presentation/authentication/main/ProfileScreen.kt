package com.akihiro.instagramclone.presentation.authentication.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.akihiro.instagramclone.presentation.authentication.BottomNavigationItem
import com.akihiro.instagramclone.presentation.authentication.BottomNavigationMenu

@Composable
fun ProfileScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.weight(1f)){
            Text(text = "Profile Screen")
        }
        BottomNavigationMenu(selectedItem = BottomNavigationItem.PROFILE, navController = navController)
    }
}
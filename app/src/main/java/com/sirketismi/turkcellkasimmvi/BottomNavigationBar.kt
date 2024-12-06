package com.sirketismi.turkcellkasimmvi

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController : NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Account,
        BottomNavItem.Favorite,
        BottomNavItem.Cart)

    BottomNavigation(modifier =  Modifier.padding(vertical = 10.dp)) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = backStackEntry?.destination

        items.forEach { item->
            BottomNavigationItem(
                selected = currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}
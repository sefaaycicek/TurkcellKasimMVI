package com.sirketismi.turkcellkasimmvi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    Scaffold(bottomBar = { BottomNavigationBar(navController) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.HOME,
            modifier = Modifier.padding(innerPadding)) {

            composable(Route.HOME) {
                ProductScreen {
                    navController.navigate(Route.PRODUCT_DETAIL)
                }
            }

            composable(Route.ACCOUNT) {
                AccountScreen()
            }

            composable(Route.CART) {
                CartScreen()
            }

            composable(Route.FAVORITE) {
                FavoriteScreen()
            }

            composable(Route.PRODUCT_LIST) {
                ProductScreen {  }
            }

            composable(Route.PRODUCT_DETAIL) {
                ProductDetailScreen()
            }

        }

    }
}


@Composable
fun HomeScreen() {
    Text("adsadasd")
}

@Composable
fun AccountScreen() {
    Text("adsadasd")
}

@Composable
fun CartScreen() {
    Text("adsadasd")
}
@Composable
fun FavoriteScreen() {
    Text("adsadasd")
}

@Composable
fun ProductDetailScreen() {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text("Ürün Detayı")
    }

}
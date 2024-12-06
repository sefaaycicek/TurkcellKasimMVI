package com.sirketismi.turkcellkasimmvi

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

object Route {
    const val HOME = "home"
    const val PRODUCT_DETAIL = "product_detail"
    const val PRODUCT_LIST = "product_list"
    const val CART = "cart"
    const val ACCOUNT = "account"
    const val FAVORITE = "favorite"
}

sealed class BottomNavItem(val route : String, val icon : ImageVector, val title : String) {
    object Home : BottomNavItem(Route.HOME, Icons.Filled.Home, "Home")
    object Account : BottomNavItem(Route.ACCOUNT, Icons.Filled.AccountCircle, "Hesabım")
    object Favorite : BottomNavItem(Route.FAVORITE, Icons.Filled.Favorite, "Favoriler")
    object Cart : BottomNavItem(Route.CART, Icons.Filled.ShoppingCart, "Sepetim")
}



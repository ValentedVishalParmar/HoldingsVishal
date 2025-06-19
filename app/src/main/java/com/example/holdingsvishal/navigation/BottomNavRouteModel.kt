package com.example.holdingsvishal.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavRouteModel<T : Any>(val name: String, val route: T, val icon: ImageVector)
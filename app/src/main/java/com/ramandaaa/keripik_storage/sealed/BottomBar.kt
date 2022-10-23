package com.ramandaaa.keripik_storage.sealed

import com.ramandaaa.keripik_storage.R

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Home : BottomBar(
        route = "add",
        title = "add",
        icon = R.drawable.icon_plus
    )

    object Profile : BottomBar(
        route = "home",
        title = "home",
        icon = R.drawable.ic_home
    )

    object Settings : BottomBar(
        route = "profile",
        title = "profile",
        icon = R.drawable.icon_company
    )
}

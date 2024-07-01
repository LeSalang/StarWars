package com.lesa.navigation

import android.net.Uri

sealed class NavigationItem(val route: String) {
    data object Films : NavigationItem("films")
    data object Persons : NavigationItem("persons?ids={ids}&title={title}") {
        fun createRoute(ids: List<Int>, title: String): String {
            return "persons?ids=${ids.joinToString("-")}&title=${Uri.encode(title)}"
        }
    }
    data object Planet : NavigationItem("planet?id={id}&title={title}") {
        fun createRoute(id: Int, title: String): String {
            return "planet?id=$id&title=${Uri.encode(title)}"
        }
    }
}

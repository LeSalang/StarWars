package com.lesa.navigation

import android.net.Uri

sealed class NavigationItem(val route: String) {
    data object Films : NavigationItem(Screen.FILMS.name)
    data object Persons : NavigationItem("${Screen.PERSONS.name}?ids={ids}&title={title}") {
        fun createRoute(ids: List<Int>, title: String): String {
            return "persons?ids=${ids.joinToString("-")}&title=${Uri.encode(title)}"
        }
    }
    data object Planet : NavigationItem("${Screen.PLANET.name}?id={id}&title={title}") {
        fun createRoute(id: Int, title: String): String {
            return "planet?id=$id&title=${Uri.encode(title)}"
        }
    }
}

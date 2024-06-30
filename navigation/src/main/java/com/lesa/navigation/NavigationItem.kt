package com.lesa.navigation

sealed class NavigationItem(val route: String) {
    data object Films : NavigationItem("films")
    data object Persons : NavigationItem("persons?ids={ids}&title={title}") {
        fun createRoute(ids: List<Int>, title: String): String {
            return "persons?ids=${ids.joinToString("-")}&title=$title"
        }
    }
}

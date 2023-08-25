package com.wantique.base.navigation

import androidx.navigation.NavDirections

interface Navigator {
    fun navigateToInit()
    fun navigateToMain()
    fun navigateByDeepLink(uri: String)
    fun navigate(id: Int)
    fun navigate(directions: NavDirections)
    fun navigateUp()
}
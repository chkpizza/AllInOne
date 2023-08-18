package com.wantique.base.navigation

interface Navigator {
    fun navigateToInit()
    fun navigateToMain()
    fun navigateByDeepLink(uri: String)
    fun navigate(id: Int)
}
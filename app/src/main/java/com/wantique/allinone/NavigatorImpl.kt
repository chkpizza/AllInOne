package com.wantique.allinone

import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.wantique.base.navigation.Navigator

class NavigatorImpl(private val navController: NavController) : Navigator {
    override fun navigateToInit() {
        val navGraph = navController.navInflater.inflate(R.navigation.app_nav_graph)
        navGraph.setStartDestination(R.id.init_nav_graph)
        navController.setGraph(navGraph, null)
    }

    override fun navigateToMain() {
        val navGraph = navController.navInflater.inflate(R.navigation.app_nav_graph)
        navGraph.setStartDestination(R.id.main_nav_graph)
        navController.setGraph(navGraph, null)
    }

    override fun navigateByDeepLink(uri: String) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(uri.toUri())
            .build()
        navController.navigate(request)
    }

    override fun navigate(id: Int) {
        navController.navigate(id)
    }

}
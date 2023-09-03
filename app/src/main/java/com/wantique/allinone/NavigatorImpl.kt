package com.wantique.allinone

import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.wantique.base.navigation.Navigator

class NavigatorImpl(private val navController: NavController) : Navigator {
    override fun navigateToInit() {
        /*
        val navGraph = navController.navInflater.inflate(R.navigation.app_nav_graph)
        navGraph.setStartDestination(R.id.init_nav_graph)
        navController.setGraph(navGraph, null)

         */
        navController.clearBackStack(R.id.app_nav_graph)
        navController.navigate(R.id.action_main_to_init)
    }

    override fun navigateToMain() {
        navController.navigate(R.id.action_init_to_main)
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

    override fun navigate(directions: NavDirections) {
        navController.navigate(directions)
    }

    override fun navigateUp() {
        navController.navigateUp()
    }

}
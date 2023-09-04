package com.wantique.allinone

import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.wantique.allinone.databinding.ActivityMainBinding
import com.wantique.base.navigation.Navigator
import com.wantique.base.navigation.NavigatorProvider
import com.wantique.base.ui.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), NavigatorProvider {
    private val navController by lazy { (supportFragmentManager.findFragmentById(R.id.app_fragment_host_view) as NavHostFragment).navController }
    private val navigator by lazy { NavigatorImpl(navController)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpBottomNavigation()
        initGraph()
    }

    private fun setUpBottomNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                com.wantique.home.R.id.homeFragment -> binding.appBottomNavigationView.isVisible = true
                com.wantique.daily.R.id.dailyFragment -> binding.appBottomNavigationView.isVisible = true
                com.wantique.mypage.R.id.myPageFragment -> binding.appBottomNavigationView.isVisible = true
                else -> binding.appBottomNavigationView.isVisible = false
            }
        }

        binding.appBottomNavigationView.setupWithNavController(navController)
    }

    private fun initGraph() {
        Log.d("S20FECauseErrorTest", "initGraph call")
        val navGraph = navController.navInflater.inflate(R.navigation.app_nav_graph)
        if(getPreferences(MODE_PRIVATE).getBoolean(getString(com.wantique.resource.R.string.common_sign_in_key), false)) {
            navGraph.setStartDestination(R.id.main_nav_graph)
        } else {
            navGraph.setStartDestination(R.id.init_nav_graph)
        }
        navController.graph = navGraph
    }

    override fun getNavigator(): Navigator {
        return navigator
    }

}
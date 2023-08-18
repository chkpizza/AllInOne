package com.wantique.allinone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        initializeNavigationGraph()
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

    private fun initializeNavigationGraph() {
        navigator.navigateToInit()
    }

    override fun getNavigator(): Navigator = navigator

}
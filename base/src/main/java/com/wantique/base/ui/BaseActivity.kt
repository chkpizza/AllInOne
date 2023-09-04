package com.wantique.base.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class BaseActivity<T: ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {
    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResId)
        setUpEdgeToEdge()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = true
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or  View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}
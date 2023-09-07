package com.wantique.allinone

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.wantique.allinone.di.DaggerAppComponent
import com.wantique.auth.di.AuthComponent
import com.wantique.auth.di.AuthComponentProvider
import com.wantique.daily.di.DailyComponent
import com.wantique.daily.di.DailyComponentProvider
import com.wantique.home.di.HomeComponent
import com.wantique.home.di.HomeComponentProvider

class NewApplication : Application(), AuthComponentProvider, HomeComponentProvider, DailyComponentProvider {
    private val appComponent by lazy { DaggerAppComponent.factory().create(this, getString(R.string.default_web_client_id)) }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun getAuthComponent(): AuthComponent {
        return appComponent.getAuthComponent().create()
    }

    override fun getHomeComponent(): HomeComponent {
        return appComponent.getHomeComponent().create()
    }

    override fun getDailyComponent(): DailyComponent {
        return appComponent.getDailyComponent().create()
    }
}
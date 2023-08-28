package com.wantique.allinone

import android.app.Application
import com.wantique.allinone.di.DaggerAppComponent
import com.wantique.auth.di.AuthComponent
import com.wantique.auth.di.AuthComponentProvider
import com.wantique.home.di.HomeComponent
import com.wantique.home.di.HomeComponentProvider

class NewApplication : Application(), AuthComponentProvider, HomeComponentProvider {
    private val appComponent by lazy { DaggerAppComponent.factory().create(this, getString(R.string.default_web_client_id)) }

    override fun getAuthComponent(): AuthComponent {
        return appComponent.getAuthComponent().create()
    }

    override fun getHomeComponent(): HomeComponent {
        return appComponent.getHomeComponent().create()
    }
}
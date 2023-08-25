package com.wantique.allinone

import android.app.Application
import com.wantique.allinone.di.DaggerAppComponent
import com.wantique.auth.WebClientIdProvider
import com.wantique.auth.ui.di.AuthComponent
import com.wantique.auth.ui.di.AuthComponentProvider
import com.wantique.home.di.HomeComponent
import com.wantique.home.di.HomeComponentProvider

class NewApplication : Application(), WebClientIdProvider, AuthComponentProvider, HomeComponentProvider {
    private val appComponent by lazy { DaggerAppComponent.factory().create(this) }

    override fun getAuthComponent(): AuthComponent {
        return appComponent.getAuthComponent().create()
    }

    override fun getHomeComponent(): HomeComponent {
        return appComponent.getHomeComponent().create()
    }

    override fun getWebClientId(): String {
        return getString(R.string.default_web_client_id)
    }
}
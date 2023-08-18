package com.wantique.allinone

import android.app.Application
import com.wantique.allinone.di.DaggerAppComponent
import com.wantique.auth.ui.di.AuthComponent
import com.wantique.auth.ui.di.AuthComponentProvider

class NewApplication : Application(), AuthComponentProvider {
    private val appComponent by lazy { DaggerAppComponent.factory().create(this) }

    override fun getAuthComponent(): AuthComponent {
        return appComponent.getAuthComponent().create()
    }
}
package com.wantique.allinone

import android.app.Application
import com.wantique.allinone.di.DaggerAppComponent
import com.wantique.auth.di.AuthComponent
import com.wantique.auth.di.AuthComponentProvider
import com.wantique.daily.di.DailyComponent
import com.wantique.daily.di.DailyComponentProvider
import com.wantique.home.di.HomeComponent
import com.wantique.home.di.HomeComponentProvider
import com.wantique.mypage.di.MyPageComponent
import com.wantique.mypage.di.MyPageComponentProvider

class NewApplication : Application(), AuthComponentProvider, HomeComponentProvider, DailyComponentProvider, MyPageComponentProvider {
    private val appComponent by lazy { DaggerAppComponent.factory().create(this, getString(R.string.default_web_client_id)) }

    override fun getAuthComponent(): AuthComponent {
        return appComponent.getAuthComponent().create()
    }

    override fun getHomeComponent(): HomeComponent {
        return appComponent.getHomeComponent().create()
    }

    override fun getDailyComponent(): DailyComponent {
        return appComponent.getDailyComponent().create()
    }

    override fun getMyPageComponent(): MyPageComponent {
        return appComponent.getMyPageComponent().create()
    }
}
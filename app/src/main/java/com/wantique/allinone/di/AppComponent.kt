package com.wantique.allinone.di

import android.content.Context
import com.wantique.auth.di.AuthComponent
import com.wantique.base.di.CoroutineDispatcherModule
import com.wantique.base.di.ViewModelFactoryModule
import com.wantique.daily.di.DailyComponent
import com.wantique.home.di.HomeComponent
import com.wantique.mypage.di.MyPageComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [SubComponentModule::class, ViewModelFactoryModule::class, FirebaseModule::class, CoroutineDispatcherModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
            @BindsInstance webClientId: String
        ): AppComponent
    }

    fun getAuthComponent(): AuthComponent.Factory
    fun getHomeComponent(): HomeComponent.Factory
    fun getDailyComponent(): DailyComponent.Factory
    fun getMyPageComponent(): MyPageComponent.Factory
}

@Module(subcomponents = [AuthComponent::class, HomeComponent::class, DailyComponent::class, MyPageComponent::class])
object SubComponentModule
package com.wantique.allinone.di

import android.content.Context
import com.wantique.auth.ui.di.AuthComponent
import com.wantique.base.di.CoroutineDispatcherModule
import com.wantique.base.di.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [SubComponentModule::class, ViewModelFactoryModule::class, CoroutineDispatcherModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context
        ): AppComponent
    }

    fun getAuthComponent(): AuthComponent.Factory

}

@Module(subcomponents = [AuthComponent::class])
object SubComponentModule
package com.wantique.auth.ui.di

import com.wantique.auth.ui.AuthFragment
import com.wantique.base.di.FeatureScope
import dagger.Subcomponent

@FeatureScope
@Subcomponent(modules = [ViewModelModule::class])
interface AuthComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    fun inject(fragment: AuthFragment)
}
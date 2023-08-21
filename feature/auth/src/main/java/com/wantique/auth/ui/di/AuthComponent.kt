package com.wantique.auth.ui.di

import com.wantique.auth.ui.AuthFragment
import com.wantique.auth.ui.VerificationFragment
import com.wantique.base.di.FeatureScope
import dagger.Subcomponent

@FeatureScope
@Subcomponent(modules = [ViewModelModule::class, RepositoryModule::class, FireStoreModule::class])
interface AuthComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    fun inject(fragment: AuthFragment)
    fun inject(fragment: VerificationFragment)
}
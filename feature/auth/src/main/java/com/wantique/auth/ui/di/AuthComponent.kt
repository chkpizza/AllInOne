package com.wantique.auth.ui.di

import com.wantique.auth.ui.view.AuthFragment
import com.wantique.auth.ui.view.CancelWithdrawalFragment
import com.wantique.auth.ui.view.SettingsFragment
import com.wantique.auth.ui.view.VerificationFragment
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
    fun inject(fragment: CancelWithdrawalFragment)
    fun inject(fragment: SettingsFragment)
}
package com.wantique.auth.di

import com.wantique.auth.ui.auth.AuthFragment
import com.wantique.auth.ui.withdrawal.CancelWithdrawalFragment
import com.wantique.auth.ui.settings.SettingsFragment
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
    fun inject(fragment: CancelWithdrawalFragment)
    fun inject(fragment: SettingsFragment)
}
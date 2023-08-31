package com.wantique.daily.di

import com.wantique.base.di.FeatureScope
import com.wantique.daily.ui.daily.DailyFragment
import dagger.Subcomponent

@FeatureScope
@Subcomponent(modules = [ViewModelModule::class, RepositoryModule::class, FireStoreModule::class])
interface DailyComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DailyComponent
    }

    fun inject(fragment: DailyFragment)
}
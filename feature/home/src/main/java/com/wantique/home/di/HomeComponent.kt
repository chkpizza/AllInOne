package com.wantique.home.di

import com.wantique.base.di.FeatureScope
import com.wantique.home.ui.home.HomeFragment
import com.wantique.home.ui.details.ProfessorDetailsFragment
import dagger.Subcomponent

@FeatureScope
@Subcomponent(modules = [ViewModelModule::class, RepositoryModule::class])
interface HomeComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }

    fun inject(fragment: HomeFragment)
    fun inject(fragment: ProfessorDetailsFragment)
}
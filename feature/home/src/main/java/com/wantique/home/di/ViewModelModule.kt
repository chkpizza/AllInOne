package com.wantique.home.di

import androidx.lifecycle.ViewModel
import com.wantique.base.di.ViewModelKey
import com.wantique.home.ui.vm.HomeViewModel
import com.wantique.home.ui.vm.ProfessorDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfessorDetailsViewModel::class)
    abstract fun bindProfessorDetailsViewModel(viewModel: ProfessorDetailsViewModel): ViewModel
}
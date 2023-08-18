package com.wantique.auth.ui.di

import androidx.lifecycle.ViewModel
import com.wantique.auth.ui.AuthViewModel
import com.wantique.base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel
}
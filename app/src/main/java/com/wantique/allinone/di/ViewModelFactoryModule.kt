package com.wantique.allinone.di

import androidx.lifecycle.ViewModelProvider
import com.wantique.base.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
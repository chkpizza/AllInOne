package com.wantique.daily.di

import androidx.lifecycle.ViewModel
import com.wantique.base.di.ViewModelKey
import com.wantique.daily.ui.daily.DailyViewModel
import com.wantique.daily.ui.promise.PromiseViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DailyViewModel::class)
    abstract fun bindDailyViewModel(viewModel: DailyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PromiseViewModel::class)
    abstract fun bindPromiseViewModel(viewModel: PromiseViewModel): ViewModel
}
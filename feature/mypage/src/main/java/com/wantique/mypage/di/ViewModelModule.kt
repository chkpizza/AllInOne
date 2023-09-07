package com.wantique.mypage.di

import androidx.lifecycle.ViewModel
import com.wantique.base.di.ViewModelKey
import com.wantique.mypage.ui.mypage.MyPageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MyPageViewModel::class)
    abstract fun bindHomeViewModel(viewModel: MyPageViewModel): ViewModel
}
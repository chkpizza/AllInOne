package com.wantique.mypage.di

import androidx.lifecycle.ViewModel
import com.wantique.base.di.ViewModelKey
import com.wantique.mypage.ui.edit.EditViewModel
import com.wantique.mypage.ui.mypage.MyPageViewModel
import com.wantique.mypage.ui.recommend.RecommendViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MyPageViewModel::class)
    abstract fun bindHomeViewModel(viewModel: MyPageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditViewModel::class)
    abstract fun bindEditViewModel(viewMode: EditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecommendViewModel::class)
    abstract fun bindRecommendViewModel(viewModel: RecommendViewModel): ViewModel
}
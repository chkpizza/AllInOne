package com.wantique.home.di

import androidx.lifecycle.ViewModel
import com.wantique.base.di.ViewModelKey
import com.wantique.home.ui.home.HomeViewModel
import com.wantique.home.ui.details.ProfessorDetailsViewModel
import com.wantique.home.ui.notice.NoticeViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(NoticeViewModel::class)
    abstract fun bindNoticeViewModel(viewModel: NoticeViewModel): ViewModel
}
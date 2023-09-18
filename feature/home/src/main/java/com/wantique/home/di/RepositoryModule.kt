package com.wantique.home.di

import com.wantique.base.di.FeatureScope
import com.wantique.firebase.Firebase
import com.wantique.home.data.repository.HomeRepositoryImpl
import com.wantique.home.data.repository.NoticeRepositoryImpl
import com.wantique.home.data.repository.ProfessorDetailsRepositoryImpl
import com.wantique.home.domain.repository.HomeRepository
import com.wantique.home.domain.repository.NoticeRepository
import com.wantique.home.domain.repository.ProfessorDetailsRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class RepositoryModule {
    @FeatureScope
    @Provides
    fun provideAuthRepository(dispatcher: CoroutineDispatcher, firebase: Firebase): HomeRepository {
        return HomeRepositoryImpl(dispatcher, firebase)
    }

    @FeatureScope
    @Provides
    fun provideProfessorDetailsRepository(dispatcher: CoroutineDispatcher, firebase: Firebase): ProfessorDetailsRepository {
        return ProfessorDetailsRepositoryImpl(dispatcher, firebase)
    }

    @FeatureScope
    @Provides
    fun provideNoticeRepository(dispatcher: CoroutineDispatcher, firebase: Firebase): NoticeRepository {
        return NoticeRepositoryImpl(dispatcher, firebase)
    }
}
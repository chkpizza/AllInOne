package com.wantique.mypage.di

import com.wantique.base.di.FeatureScope
import com.wantique.firebase.Firebase
import com.wantique.mypage.data.repository.MyPageRepositoryImpl
import com.wantique.mypage.domain.repository.MyPageRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class RepositoryModule {
    @FeatureScope
    @Provides
    fun provideAuthRepository(dispatcher: CoroutineDispatcher, firebase: Firebase): MyPageRepository {
        return MyPageRepositoryImpl(dispatcher, firebase)
    }
}
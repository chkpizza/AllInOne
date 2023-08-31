package com.wantique.daily.di

import com.wantique.base.di.FeatureScope
import com.wantique.daily.data.DailyRepositoryImpl
import com.wantique.daily.domain.repository.DailyRepository
import com.wantique.firebase.Firebase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class RepositoryModule {
    @FeatureScope
    @Provides
    fun provideDailyRepository(dispatcher: CoroutineDispatcher, firebase: Firebase): DailyRepository {
        return DailyRepositoryImpl(dispatcher, firebase)
    }
}
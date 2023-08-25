package com.wantique.home.di

import com.wantique.base.di.FeatureScope
import com.wantique.firebase.Firebase
import com.wantique.home.data.repository.HomeRepositoryImpl
import com.wantique.home.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class RepositoryModule {
    @FeatureScope
    @Provides
    fun provideAuthRepository(dispatcher: CoroutineDispatcher, firestore: Firebase): HomeRepository {
        return HomeRepositoryImpl(dispatcher, firestore)
    }
}
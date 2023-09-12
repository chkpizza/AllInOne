package com.wantique.auth.di

import com.wantique.auth.data.repository.AuthRepositoryImpl
import com.wantique.auth.domain.repository.AuthRepository
import com.wantique.base.di.FeatureScope
import com.wantique.firebase.Firebase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class RepositoryModule {
    @FeatureScope
    @Provides
    fun provideAuthRepository(dispatcher: CoroutineDispatcher, firebase: Firebase): AuthRepository {
        return AuthRepositoryImpl(dispatcher, firebase)
    }
}
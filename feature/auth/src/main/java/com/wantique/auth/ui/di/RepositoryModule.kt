package com.wantique.auth.ui.di

import com.wantique.auth.data.repository.AuthRepositoryImpl
import com.wantique.auth.domain.repository.AuthRepository
import com.wantique.base.di.FeatureScope
import com.wantique.firebase.FireStore
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class RepositoryModule {
    /*
    @Provides
    @FeatureScope
    fun provideAuthRepository(dispatcher: CoroutineDispatcher): TestRepository {
        return TestRepositoryImpl(dispatcher)
    }
     */

    @FeatureScope
    @Provides
    fun provideAuthRepository(dispatcher: CoroutineDispatcher, firestore: FireStore): AuthRepository {
        return AuthRepositoryImpl(dispatcher, firestore)
    }
}
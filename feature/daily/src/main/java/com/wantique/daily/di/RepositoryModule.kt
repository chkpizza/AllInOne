package com.wantique.daily.di

import com.wantique.base.di.FeatureScope
import com.wantique.firebase.Firebase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class RepositoryModule {
    /*
    @FeatureScope
    @Provides
    fun provideAuthRepository(dispatcher: CoroutineDispatcher, firestore: Firebase): HomeRepository {
        return HomeRepositoryImpl(dispatcher, firestore)
    }

    @FeatureScope
    @Provides
    fun provideProfessorDetailsRepository(dispatcher: CoroutineDispatcher, firebase: Firebase): ProfessorDetailsRepository {
        return ProfessorDetailsRepositoryImpl(dispatcher, firebase)
    }

     */
}
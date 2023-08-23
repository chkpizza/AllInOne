package com.wantique.home.di

import com.wantique.base.di.FeatureScope
import com.wantique.firebase.FireStore
import dagger.Module
import dagger.Provides

@Module
class FireStoreModule {
    @FeatureScope
    @Provides
    fun provideFireStore(): FireStore {
        return FireStore.getInstance()
    }
}
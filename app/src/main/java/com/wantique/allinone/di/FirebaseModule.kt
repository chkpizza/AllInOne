package com.wantique.allinone.di

import com.wantique.firebase.Firebase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {
    @Singleton
    @Provides
    fun provideFirebase(): Firebase {
        return Firebase.getInstance()
    }
}
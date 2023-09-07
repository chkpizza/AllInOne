package com.wantique.mypage.di

import com.wantique.base.di.FeatureScope
import com.wantique.firebase.Firebase
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @FeatureScope
    @Provides
    fun provideFireStore(): Firebase {
        return Firebase.getInstance()
    }

}
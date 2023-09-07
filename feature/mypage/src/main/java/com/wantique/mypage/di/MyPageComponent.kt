package com.wantique.mypage.di

import com.wantique.base.di.FeatureScope
import com.wantique.mypage.ui.mypage.MyPageFragment
import dagger.Subcomponent

@FeatureScope
@Subcomponent(modules = [ViewModelModule::class, RepositoryModule::class, FirebaseModule::class])
interface MyPageComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MyPageComponent
    }

    fun inject(fragment: MyPageFragment)
}
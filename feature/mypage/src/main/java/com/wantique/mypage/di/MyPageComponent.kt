package com.wantique.mypage.di

import com.wantique.base.di.FeatureScope
import com.wantique.mypage.ui.edit.EditFragment
import com.wantique.mypage.ui.edit.EditProfileFragment
import com.wantique.mypage.ui.edit.WithdrawalFragment
import com.wantique.mypage.ui.mypage.MyPageFragment
import com.wantique.mypage.ui.recommend.RecommendFragment
import dagger.Subcomponent

@FeatureScope
@Subcomponent(modules = [ViewModelModule::class, RepositoryModule::class])
interface MyPageComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MyPageComponent
    }

    fun inject(fragment: MyPageFragment)
    fun inject(fragment: EditFragment)
    fun inject(fragment: EditProfileFragment)
    fun inject(fragment: WithdrawalFragment)
    fun inject(fragment: RecommendFragment)
}
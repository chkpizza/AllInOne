package com.wantique.daily.di

import com.wantique.base.di.FeatureScope
import com.wantique.daily.ui.daily.DailyFragment
import com.wantique.daily.ui.pastExam.PastExamFragment
import com.wantique.daily.ui.record.RecordFragment
import com.wantique.daily.ui.record.WriteRecordFragment
import dagger.Subcomponent

@FeatureScope
@Subcomponent(modules = [ViewModelModule::class, RepositoryModule::class, FireStoreModule::class])
interface DailyComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DailyComponent
    }

    fun inject(fragment: DailyFragment)
    fun inject(fragment: WriteRecordFragment)
    fun inject(fragment: RecordFragment)
    fun inject(fragment: PastExamFragment)
}
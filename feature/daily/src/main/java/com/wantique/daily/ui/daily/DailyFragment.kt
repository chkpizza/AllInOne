package com.wantique.daily.ui.daily

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.wantique.base.ui.BaseFragment
import com.wantique.daily.R
import com.wantique.daily.databinding.FragmentDailyBinding
import com.wantique.daily.di.DailyComponentProvider
import javax.inject.Inject


class DailyFragment : BaseFragment<FragmentDailyBinding>(R.layout.fragment_daily) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[DailyViewModel::class.java] }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as DailyComponentProvider).getDailyComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        updateTopInsets()
        setUpViewListener()
    }

    private fun setUpViewListener() {
        //TODO 테스트를 위한 임시 리스너
        binding.dailyToolbar.setOnClickListener {
            navigator.navigate(R.id.action_dailyFragment_to_writeRecordFragment)
        }
    }
}
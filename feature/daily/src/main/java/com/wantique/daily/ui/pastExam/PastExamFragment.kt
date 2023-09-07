package com.wantique.daily.ui.pastExam

import android.content.Context
import android.os.Bundle
import android.util.Log

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.wantique.base.ui.BaseFragment
import com.wantique.daily.R
import com.wantique.daily.databinding.FragmentPastExamBinding
import com.wantique.daily.di.DailyComponentProvider
import com.wantique.daily.ui.pastExam.adapter.PastExamAdapter
import javax.inject.Inject

class PastExamFragment : BaseFragment<FragmentPastExamBinding>(R.layout.fragment_past_exam) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[PastExamViewModel::class.java]}

    private val args: PastExamFragmentArgs by navArgs()
    private lateinit var pastExamAdapter: PastExamAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as DailyComponentProvider).getDailyComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateInsets()
        setUpViewPager()
    }

    private fun setUpViewPager() {
        pastExamAdapter = PastExamAdapter()
        binding.pastExamVp.adapter = pastExamAdapter
        binding.pastExamVp.offscreenPageLimit = args.pastExam.size
        pastExamAdapter.submitList(args.pastExam.toList())
    }

}
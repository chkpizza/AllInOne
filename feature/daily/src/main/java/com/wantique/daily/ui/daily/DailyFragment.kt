package com.wantique.daily.ui.daily

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.wantique.base.state.isSuccessOrNull
import com.wantique.base.ui.BaseFragment
import com.wantique.daily.R
import com.wantique.daily.databinding.FragmentDailyBinding
import com.wantique.daily.di.DailyComponentProvider
import com.wantique.daily.domain.model.PastExam
import com.wantique.daily.domain.model.Promise
import com.wantique.daily.ui.daily.adapter.DailyAdapter
import com.wantique.daily.ui.daily.adapter.listener.OnPastExamClickListener
import com.wantique.daily.ui.daily.adapter.listener.OnPromisePreviewClickListener
import com.wantique.daily.ui.daily.adapter.listener.OnWritePromiseClickListener
import com.wantique.firebase.Firebase
import kotlinx.coroutines.launch
import javax.inject.Inject


class DailyFragment : BaseFragment<FragmentDailyBinding>(R.layout.fragment_daily) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[DailyViewModel::class.java] }
    private lateinit var dailyAdapter: DailyAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as DailyComponentProvider).getDailyComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        updateTopInsets()
        setUpRecyclerView()
        setUpViewListener()
        request()
    }

    private fun setUpRecyclerView() {
        val onWritePromiseClickListener = object : OnWritePromiseClickListener {
            override fun onClick() {
                navigator.navigate(R.id.action_dailyFragment_to_writePromiseFragment)
            }
        }

        val onPromisePreviewClickListener = object : OnPromisePreviewClickListener {
            override fun onClick(position: Int, promise: List<Promise>) {
                Toast.makeText(requireActivity(), "$position $promise", Toast.LENGTH_SHORT).show()
            }
        }

        val onPastExamClickListener = object : OnPastExamClickListener {
            override fun onClick(position: Int, pastExam: List<PastExam>) {
                Toast.makeText(requireActivity(), "$position $pastExam", Toast.LENGTH_SHORT).show()
            }
        }

        dailyAdapter = DailyAdapter(onWritePromiseClickListener, onPromisePreviewClickListener, onPastExamClickListener)
        binding.dailyRv.adapter = dailyAdapter
    }

    private fun setUpViewListener() {
        binding.dailyLayoutError.networkErrorBtnRetry.setOnClickListener {
            viewModel.fetchDaily()
        }
    }

    private fun request() {
        viewModel.fetchDaily()
    }
}
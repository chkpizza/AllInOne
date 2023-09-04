package com.wantique.daily.ui.daily

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.wantique.base.ui.BaseFragment
import com.wantique.daily.R
import com.wantique.daily.databinding.FragmentDailyBinding
import com.wantique.daily.di.DailyComponentProvider
import com.wantique.daily.domain.model.Record
import com.wantique.daily.ui.daily.adapter.DailyAdapter
import com.wantique.daily.ui.daily.adapter.listener.OnRecordClickListener
import com.wantique.firebase.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        setUpViewListener()
        setUpRecyclerView()
        request()
    }

    private fun setUpViewListener() {
        binding.dailyFab.setOnClickListener {
            navigator.navigate(R.id.action_dailyFragment_to_writeRecordFragment)
        }

        binding.dailyLayoutError.networkErrorBtnRetry.setOnClickListener {
            request()
        }

        binding.dailyToolbar.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    requireActivity().getPreferences(Context.MODE_PRIVATE).edit().putBoolean(getString(com.wantique.resource.R.string.common_sign_in_key), false).apply()
                }
                Firebase.getInstance().signOut()
                navigator.navigateToInit()
            }
        }
    }

    private fun setUpRecyclerView() {
        val onRecordClickListener = object : OnRecordClickListener {
            override fun onClick(position: Int, record: List<Record>) {
                val action = DailyFragmentDirections.actionDailyFragmentToRecordFragment(position, record.toTypedArray())
                navigator.navigate(action)
            }
        }

        dailyAdapter = DailyAdapter(onRecordClickListener)
        binding.dailyRv.adapter = dailyAdapter
    }

    private fun request() {
        viewModel.fetchDaily()
    }
}
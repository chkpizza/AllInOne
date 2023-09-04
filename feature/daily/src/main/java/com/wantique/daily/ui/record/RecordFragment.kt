package com.wantique.daily.ui.record

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.wantique.base.ui.BaseFragment
import com.wantique.daily.R
import com.wantique.daily.databinding.FragmentRecordBinding
import com.wantique.daily.di.DailyComponentProvider
import com.wantique.daily.domain.model.Record
import com.wantique.daily.ui.record.adapter.TodayRecordAdapter
import com.wantique.daily.ui.record.adapter.listener.OnRecordReportClickListener
import javax.inject.Inject

class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[RecordViewModel::class.java]}
    private val args: RecordFragmentArgs by navArgs()
    private lateinit var todayRecordAdapter: TodayRecordAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as DailyComponentProvider).getDailyComponent().inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateBottomInsets()
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val onRecordReportClickListener = object : OnRecordReportClickListener {
            override fun onClick(record: Record) {
                Toast.makeText(requireActivity(), "${record.body} 게시글 신고", Toast.LENGTH_SHORT).show()
            }
        }
        todayRecordAdapter = TodayRecordAdapter(onRecordReportClickListener)
        binding.recordVpTodayRecord.adapter = todayRecordAdapter
        todayRecordAdapter.submitList(args.record.toList())

        binding.recordVpTodayRecord.setCurrentItem(args.position, false)
    }
}
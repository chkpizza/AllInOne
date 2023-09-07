package com.wantique.daily.ui.record

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wantique.base.ui.BaseFragment
import com.wantique.daily.R
import com.wantique.daily.databinding.FragmentRecordBinding
import com.wantique.daily.databinding.LayoutBottomSheetDialogBinding
import com.wantique.daily.di.DailyComponentProvider
import com.wantique.daily.domain.model.Record
import com.wantique.daily.ui.record.adapter.ReportAdapter
import com.wantique.daily.ui.record.adapter.TodayRecordAdapter
import com.wantique.daily.ui.record.adapter.listener.OnRecordRemoveClickListener
import com.wantique.daily.ui.record.adapter.listener.OnRecordReportClickListener
import com.wantique.resource.AppDialog
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
        setUpObserver()
    }

    private fun setUpViewPager() {
        val onRecordReportClickListener = object : OnRecordReportClickListener {
            override fun onClick(record: Record) {
                val dialog = BottomSheetDialog(requireActivity())
                val binding = LayoutBottomSheetDialogBinding.inflate(LayoutInflater.from(requireActivity()))
                val adapter = ReportAdapter()

                dialog.setContentView(binding.root)
                binding.bottomSheetDialogRv.adapter = adapter
                adapter.submitList(resources.getStringArray(com.wantique.resource.R.array.today_record_report_reason).toList())
                binding.bottomSheetDialogBtn.setOnClickListener {
                    if(adapter.selectedPosition == -1) {
                        Toast.makeText(requireActivity(), "신고 사유를 선택해주세요", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.reportRecord(record.documentId, resources.getStringArray(com.wantique.resource.R.array.today_record_report_reason)[adapter.selectedPosition])
                        dialog.dismiss()
                    }
                }

                dialog.show()
            }
        }

        val onRecordRemoveClickListener = object : OnRecordRemoveClickListener {
            override fun onClick(record: Record) {
                AppDialog.Builder(requireActivity())
                    .setBody("해당 기록을 삭제하시겠습니까?")
                    .setPositiveButtonText("삭제하기")
                    .setNegativeButtonText("취소하기")
                    .setPositionButtonClickListener {
                        viewModel.removeRecord(record.documentId)
                        it.dismiss()
                    }
                    .setNegativeButtonClickListener {
                        it.dismiss()
                    }
                    .show()
            }
        }

        todayRecordAdapter = TodayRecordAdapter(onRecordReportClickListener, onRecordRemoveClickListener)
        binding.recordVpTodayRecord.adapter = todayRecordAdapter
        todayRecordAdapter.submitList(args.record.toList())

        binding.recordVpTodayRecord.setCurrentItem(args.position, false)
    }

    private fun setUpObserver() {
        viewModel.report.asLiveData().observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireActivity(), "해당 게시글을 신고하였습니다\n해당 게시글은 검토 후 조치할 예정입니다", Toast.LENGTH_SHORT).show()
                navigator.navigateUp()
            }
        }

        viewModel.remove.asLiveData().observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireActivity(), "해당 기록을 정상적으로 삭제하였습니다", Toast.LENGTH_SHORT).show()
                navigator.navigateUp()
            }
        }
    }
}
package com.wantique.home.ui.notice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.wantique.base.ui.BaseFragment
import com.wantique.home.R
import com.wantique.home.databinding.FragmentNoticeListBinding
import com.wantique.home.di.HomeComponentProvider
import com.wantique.home.ui.notice.adapter.NoticeListAdapter
import com.wantique.home.ui.notice.adapter.OnNoticeClickListener
import javax.inject.Inject

class NoticeListFragment : BaseFragment<FragmentNoticeListBinding>(R.layout.fragment_notice_list) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[NoticeViewModel::class.java]}

    private lateinit var noticeListAdapter: NoticeListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as HomeComponentProvider).getHomeComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        updateInsets()
        setUpRecyclerView()
        setUpViewListener()
        request()
    }

    private fun setUpRecyclerView() {
        val onNoticeClickListener = object : OnNoticeClickListener {
            override fun onClick(id: String) {
                navigator.navigate(NoticeListFragmentDirections.actionNoticeListFragmentToNoticeFragment(id))
            }
        }

        noticeListAdapter = NoticeListAdapter(onNoticeClickListener)
        binding.noticeListRv.adapter = noticeListAdapter
    }

    private fun setUpViewListener() {
        binding.noticeListToolbar.setNavigationOnClickListener {
            navigator.navigateUp()
        }
    }

    private fun request() {
        viewModel.fetchAllNotice()
    }
}
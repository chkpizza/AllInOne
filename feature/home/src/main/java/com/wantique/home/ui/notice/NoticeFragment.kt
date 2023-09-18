package com.wantique.home.ui.notice

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.wantique.base.ui.BaseFragment
import com.wantique.home.R
import com.wantique.home.databinding.FragmentNoticeBinding
import com.wantique.home.di.HomeComponentProvider
import javax.inject.Inject

class NoticeFragment : BaseFragment<FragmentNoticeBinding>(R.layout.fragment_notice) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[NoticeViewModel::class.java] }

    private val args: NoticeFragmentArgs by navArgs()
    private val id by lazy { args.id }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as HomeComponentProvider).getHomeComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        updateInsets()
        setUpViewListener()
        request()
    }

    private fun setUpViewListener() {
        binding.noticeToolbar.setNavigationOnClickListener {
            navigator.navigateUp()
        }

        binding.noticeTvLink.setOnClickListener {
            (binding.noticeTvLink.tag as? String)?.let { link ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
            }
        }
    }

    private fun request() {
        viewModel.fetchNotice(id)
    }
}
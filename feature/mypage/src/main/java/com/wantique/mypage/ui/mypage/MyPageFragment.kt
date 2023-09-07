package com.wantique.mypage.ui.mypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.wantique.base.state.isSuccessOrNull
import com.wantique.base.ui.BaseFragment
import com.wantique.mypage.R
import com.wantique.mypage.databinding.FragmentMyPageBinding
import com.wantique.mypage.di.MyPageComponentProvider
import javax.inject.Inject

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[MyPageViewModel::class.java]}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyPageComponentProvider).getMyPageComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        updateInsets()
        setUpViewListener()
        request()
    }

    private fun setUpViewListener() {
        binding.myPageSignOut.setOnClickListener {

        }
    }

    private fun request() {
        viewModel.fetch()
    }
}
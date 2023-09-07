package com.wantique.mypage.ui.mypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.wantique.base.state.isSuccessOrNull
import com.wantique.base.ui.BaseFragment
import com.wantique.firebase.Firebase
import com.wantique.mypage.R
import com.wantique.mypage.databinding.FragmentMyPageBinding
import com.wantique.mypage.di.MyPageComponentProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    requireActivity().getPreferences(Context.MODE_PRIVATE).edit().putBoolean(getString(com.wantique.resource.R.string.common_sign_in_key), false).apply()
                }
                Firebase.getInstance().signOut()
                navigator.navigateToInit()
            }
        }

        binding.myPageIvProfile.setOnClickListener {
            navigator.navigate(R.id.action_myPageFragment_to_editFragment)
        }
    }

    private fun request() {
        viewModel.fetch()
    }
}
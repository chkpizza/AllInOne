package com.wantique.mypage.ui.edit

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.wantique.base.ui.BaseFragment
import com.wantique.firebase.Firebase
import com.wantique.mypage.R
import com.wantique.mypage.databinding.FragmentWithdrawalBinding
import com.wantique.mypage.di.MyPageComponentProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WithdrawalFragment : BaseFragment<FragmentWithdrawalBinding>(R.layout.fragment_withdrawal) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[EditViewModel::class.java]}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyPageComponentProvider).getMyPageComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateInsets()
        setUpViewListener()
        setUpObserver()
    }

    private fun setUpViewListener() {
        binding.withdrawalCb.setOnCheckedChangeListener { _, isChecked ->
            binding.withdrawalBtnWithdrawal.isEnabled = isChecked
        }

        binding.withdrawalBtnWithdrawal.setOnClickListener {
            viewModel.withdrawalUser()
        }

        binding.withdrawalBtnWithdrawal.isEnabled = false
    }

    private fun setUpObserver() {
        viewModel.withdrawal.asLiveData().observe(viewLifecycleOwner) {
            it?.let { result ->
                if(result) {
                    Toast.makeText(requireActivity(), "회원 탈퇴처리가 완료되었습니다", Toast.LENGTH_SHORT).show()
                    finalize()
                } else {
                    Toast.makeText(requireActivity(), "회원 탈퇴 처리 중 문제가 발생하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun finalize() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                requireActivity().getPreferences(Context.MODE_PRIVATE).edit().putBoolean(getString(com.wantique.resource.R.string.common_sign_in_key), false).apply()
            }
            Firebase.getInstance().signOut()
            navigator.navigateToInit()
        }
    }
}
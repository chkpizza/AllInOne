package com.wantique.auth.ui.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navGraphViewModels
import com.wantique.auth.R
import com.wantique.auth.databinding.FragmentCancelWithdrawalBinding
import com.wantique.auth.ui.di.AuthComponentProvider
import com.wantique.auth.ui.vm.AuthViewModel
import com.wantique.base.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CancelWithdrawalFragment : BaseFragment<FragmentCancelWithdrawalBinding>(R.layout.fragment_cancel_withdrawal) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by navGraphViewModels<AuthViewModel>(R.id.auth_nav_graph) { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AuthComponentProvider).getAuthComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateInsets()
        setUpViewListener()
        setUpObserver()
    }

    private fun setUpViewListener() {
        binding.cancelWithdrawalBtnRedo.setOnClickListener {
            viewModel.redoUser()
        }
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.redo.collect {
                    when(it) {
                        true -> finalize()
                        false -> Toast.makeText(requireActivity(), "탈퇴 철회에 실패하였습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun finalize() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                requireActivity().getPreferences(Context.MODE_PRIVATE).edit().putBoolean(getString(com.wantique.resource.R.string.common_sign_in_key), true).apply()
            }
            navigator.navigateToMain()
        }
    }
}
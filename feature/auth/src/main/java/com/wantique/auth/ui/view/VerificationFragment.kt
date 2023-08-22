package com.wantique.auth.ui.view

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navGraphViewModels
import com.wantique.auth.R
import com.wantique.auth.databinding.FragmentVerificationBinding
import com.wantique.auth.ui.vm.AuthViewModel
import com.wantique.auth.ui.di.AuthComponentProvider
import com.wantique.base.ui.BaseFragment
import com.wantique.firebase.FireStore
import com.wantique.firebase.FirebaseAuth
import com.wantique.firebase.listener.OnVerificationCredentialCallback
import com.wantique.firebase.listener.OnVerificationStateCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VerificationFragment :
    BaseFragment<FragmentVerificationBinding>(R.layout.fragment_verification) {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by navGraphViewModels<AuthViewModel>(R.id.auth_nav_graph) { factory }
    private var verificationId: String? = null
    private val onVerificationStateCallback = object : OnVerificationStateCallback {
        /** 잘못된 전화번호, 인증 번호 요청 횟수 초과 등 예외 발생 시 호출 */
        override fun onVerificationFailed(message: String) {
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
        }

        /** 인증 번호 발송 성공 시 호출 */
        override fun onCodeSent(verificationId: String) {
            this@VerificationFragment.verificationId = verificationId
            binding.verificationGroup.isVisible = true
            viewModel.startTimer(120)
        }
    }

    private val onVerificationCredentialCallback = object : OnVerificationCredentialCallback {
        /** 인증 번호 검증 성공 시 호출 */
        override fun onSuccess() {
            viewModel.isExistUser()
        }

        /** 인증 번호 검증 실패 시 호출 예) 시간 만료 등 */
        override fun onFailure(message: String) {
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AuthComponentProvider).getAuthComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        updateInsets()
        setUpViewListener()
        setUpObservers()
    }

    private fun setUpViewListener() {
        binding.verificationEtInputPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    binding.verificationBtnRequestCode.isEnabled = it.length >= 11
                }
            }
        })

        binding.verificationEtInputCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    binding.verificationBtnVerifyCode.isEnabled = it.length >= 6
                }
            }
        })

        binding.verificationBtnRequestCode.setOnClickListener {
            requestCode()
        }

        binding.verificationBtnVerifyCode.setOnClickListener {
            verifyCode()
        }

        binding.verificationBtnRequestCode.isEnabled = false
        binding.verificationBtnVerifyCode.isEnabled = false
    }

    private fun requestCode() {
        FirebaseAuth.getInstance().verifyPhoneNumber(requireActivity(), binding.verificationEtInputPhoneNumber.text.toString(), onVerificationStateCallback)
    }

    private fun verifyCode() {
        FirebaseAuth.getInstance().verifyCode(verificationId!!, binding.verificationEtInputCode.text.toString(), onVerificationCredentialCallback)
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.exist.collect {
                    when(it) {
                        true -> viewModel.isWithdrawalUser()
                        false -> viewModel.registerUser()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.withdrawal.collect {
                    when(it) {
                        true -> navigator.navigate(R.id.action_verificationFragment_to_cancelWithdrawalFragment)
                        false -> finalize()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registration.collect {
                    when(it) {
                        true -> finalize()
                        false -> Toast.makeText(requireActivity(), "사용자 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun finalize() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                requireActivity().getPreferences(MODE_PRIVATE).edit().putBoolean(getString(com.wantique.resource.R.string.common_sign_in_key), true).apply()
            }
            navigator.navigateToMain()
        }
    }
}
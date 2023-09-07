package com.wantique.auth.ui.settings

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.wantique.auth.R
import com.wantique.auth.databinding.FragmentSettingsBinding
import com.wantique.auth.di.AuthComponentProvider
import com.wantique.auth.ui.auth.AuthViewModel
import com.wantique.base.state.isSuccessOrNull
import com.wantique.base.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by navGraphViewModels<AuthViewModel>(R.id.auth_nav_graph) { factory }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == RESULT_OK) {
            result.data?.data?.let {
                viewModel.setUri(it)
            }
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
        setUpObserver()
    }

    private fun setUpViewListener() {
        binding.settingsToolbar.setNavigationOnClickListener {
            navigator.navigateUp()
        }

        binding.settingsIvProfile.setOnClickListener {
            galleryLauncher.launch(Intent().also {
                it.type = "image/*"
                it.action = Intent.ACTION_GET_CONTENT
            })
        }

        binding.settingsEtNickName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    binding.settingsBtnRegistration.isEnabled = it.length >= 2
                }
            }
        })

        binding.settingsBtnRegistration.setOnClickListener {
            viewModel.checkNickName(binding.settingsEtNickName.text.toString().replace(" ", ""))
        }

        binding.settingsLayoutError.networkErrorBtnRetry.setOnClickListener {
            retry()
        }

        binding.settingsBtnRegistration.isEnabled = false
    }

    private fun setUpObserver() {
        viewModel.uri.asLiveData().observe(viewLifecycleOwner) {
            it.isSuccessOrNull()?.let { uri ->
                Glide.with(binding.settingsIvProfile.context)
                    .load(uri)
                    .into(binding.settingsIvProfile)
            }
        }

        viewModel.registration.asLiveData().observe(viewLifecycleOwner) {
            when(it) {
                true -> finalize()
                false -> Toast.makeText(requireActivity(), "사용자 등록에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun retry() {
        viewModel.checkNickName(binding.settingsEtNickName.text.toString().replace(" ", ""))
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
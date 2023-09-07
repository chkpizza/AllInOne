package com.wantique.mypage.ui.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.wantique.base.ui.BaseFragment
import com.wantique.mypage.R
import com.wantique.mypage.databinding.FragmentEditProfileBinding
import com.wantique.mypage.di.MyPageComponentProvider
import javax.inject.Inject

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[EditViewModel::class.java]}

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let {
                viewModel.setProfileUri(it)
            }
        }
    }

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
        setUpObserver()
        request()
    }

    private fun setUpViewListener() {
        binding.editProfileToolbar.setNavigationOnClickListener {
            navigator.navigateUp()
        }

        binding.editProfileIvProfile.setOnClickListener {
            galleryLauncher.launch(Intent().also {
                it.type = "image/*"
                it.action = Intent.ACTION_GET_CONTENT
            })
        }

        binding.editProfileEtNickName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    binding.editProfileBtnEdit.isEnabled = it.length >= 2
                }
            }
        })

        binding.editProfileBtnEdit.setOnClickListener {
            viewModel.checkValidNickName(binding.editProfileEtNickName.text.toString())
        }

        binding.editProfileBtnEdit.isEnabled = false
    }

    private fun setUpObserver() {
        viewModel.modify.asLiveData().observe(viewLifecycleOwner) {
            it?.let { result ->
                Log.d("resultTest", result.toString())
                if(!result) {
                    Toast.makeText(requireActivity(), "프로필을 수정하지 못했습니다", Toast.LENGTH_SHORT).show()
                }
                navigator.navigateUp()
            }
        }
    }

    private fun request() {
        viewModel.fetch()
    }
}
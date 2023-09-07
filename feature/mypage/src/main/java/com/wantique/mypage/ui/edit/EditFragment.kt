package com.wantique.mypage.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wantique.base.ui.BaseFragment
import com.wantique.mypage.R
import com.wantique.mypage.databinding.FragmentEditBinding

class EditFragment : BaseFragment<FragmentEditBinding>(R.layout.fragment_edit) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateTopInsets()
        setUpViewListener()
    }

    private fun setUpViewListener() {
        binding.editToolbar.setNavigationOnClickListener {
            navigator.navigateUp()
        }

        binding.editTvEditProfile.setOnClickListener {
            navigator.navigate(R.id.action_editFragment_to_editProfileFragment)
        }

        binding.editTvWithdrawal.setOnClickListener {
            navigator.navigate(R.id.action_editFragment_to_withdrawalFragment)
        }
    }
}
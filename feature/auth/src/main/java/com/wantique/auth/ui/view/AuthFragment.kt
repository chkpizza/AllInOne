package com.wantique.auth.ui.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.wantique.auth.R
import com.wantique.auth.databinding.FragmentAuthBinding
import com.wantique.auth.ui.vm.AuthViewModel
import com.wantique.auth.ui.di.AuthComponentProvider
import com.wantique.base.ui.BaseFragment
import javax.inject.Inject


class AuthFragment : BaseFragment<FragmentAuthBinding>(R.layout.fragment_auth) {
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
    }

    private fun setUpViewListener() {
        binding.authBtnSignIn.setOnClickListener { navigator.navigate(R.id.action_authFragment_to_verificationFragment) }
    }
}
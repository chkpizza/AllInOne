package com.wantique.auth.ui.auth

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wantique.auth.R
import com.wantique.auth.databinding.FragmentAuthBinding
import com.wantique.auth.di.AuthComponentProvider
import com.wantique.base.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthFragment : BaseFragment<FragmentAuthBinding>(R.layout.fragment_auth) {
    @Inject lateinit var webClientId: String
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by navGraphViewModels<AuthViewModel>(R.id.auth_nav_graph) { factory }

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == RESULT_OK) {
            result.data?.let {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {

                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AuthComponentProvider).getAuthComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(requireActivity().getPreferences(MODE_PRIVATE).getBoolean(getString(com.wantique.resource.R.string.common_sign_in_key), false)) {
            navigator.navigateToMain()
        } else {
            binding.lifecycleOwner = this
            binding.vm = viewModel

            updateBottomInsets()
            setUpViewListener()
            setUpObservers()
            request()
        }
    }


    private fun request() {
        viewModel.getCoverImage()
    }

    private fun setUpViewListener() {
        binding.authBtnSignIn.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()

        val signInClient = GoogleSignIn.getClient(requireActivity(), gso)
        googleSignInLauncher.launch(signInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if(task.isSuccessful) {
                    viewModel.isExistUser()
                } else {
                    Toast.makeText(requireActivity(), "signIn failure: ${task.exception}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setUpObservers() {
        viewModel.exist.asLiveData().observe(viewLifecycleOwner) {
            when(it) {
                true -> viewModel.isWithdrawalUser()
                false -> navigator.navigate(R.id.action_authFragment_to_settingsFragment)
            }
        }

        viewModel.withdrawal.asLiveData().observe(viewLifecycleOwner) {
            when(it) {
                true -> { navigator.navigate(R.id.action_authFragment_to_cancelWithdrawalFragment) }
                false -> finalize()
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
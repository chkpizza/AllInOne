package com.wantique.mypage.ui.recommend

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.wantique.base.state.isSuccessOrNull
import com.wantique.base.ui.BaseFragment
import com.wantique.mypage.R
import com.wantique.mypage.databinding.FragmentRecommendBinding
import com.wantique.mypage.di.MyPageComponentProvider
import javax.inject.Inject

class RecommendFragment : BaseFragment<FragmentRecommendBinding>(R.layout.fragment_recommend) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[RecommendViewModel::class.java]}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyPageComponentProvider).getMyPageComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        updateInsets()
        setUpViewListener()
        setUpObserver()
    }

    private fun setUpViewListener() {
        binding.recommendToolbar.setNavigationOnClickListener {
            navigator.navigateUp()
        }

        binding.recommendEtBody.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    binding.recommendBtnRegistration.isEnabled = it.length >= 10
                }
            }
        })

        binding.recommendBtnRegistration.setOnClickListener {
            viewModel.registerRecommend(binding.recommendEtBody.text.toString())
        }

        binding.recommendBtnRegistration.isEnabled = false
    }


    private fun setUpObserver() {
        viewModel.recommend.asLiveData().observe(viewLifecycleOwner) {
            it.isSuccessOrNull()?.let {
                Toast.makeText(requireActivity(), "제안해주셔서 감사드립니다.\n검토 후 향후 서비스에 적극 반영하도록 하겠습니다", Toast.LENGTH_SHORT).show()
                navigator.navigateUp()
            }
        }
    }
}
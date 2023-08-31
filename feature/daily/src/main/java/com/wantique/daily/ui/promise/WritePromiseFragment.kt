package com.wantique.daily.ui.promise

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.wantique.base.state.getValue
import com.wantique.base.ui.BaseFragment
import com.wantique.daily.R
import com.wantique.daily.databinding.FragmentWritePromiseBinding
import com.wantique.daily.di.DailyComponentProvider
import com.wantique.firebase.Firebase
import kotlinx.coroutines.launch
import javax.inject.Inject

class WritePromiseFragment : BaseFragment<FragmentWritePromiseBinding>(R.layout.fragment_write_promise) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this,factory)[PromiseViewModel::class.java] }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let {
                viewModel.setUri(it)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as DailyComponentProvider).getDailyComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.galleryButton.setOnClickListener {
            galleryLauncher.launch(Intent().also {
                it.type = "image/*"
                it.action = Intent.ACTION_GET_CONTENT
            })
        }

        binding.writePromiseButton.setOnClickListener {
            writePromiseTest()
        }
    }

    private fun writePromiseTest() {
        lifecycleScope.launch {
            val result = Firebase.getInstance().writePromise(viewModel.uri.value.getValue().toString(), "오늘의 다짐 테스트 중입니다")
            if(result) {
                Toast.makeText(requireActivity(), "등록 성공!", Toast.LENGTH_SHORT).show()
                navigator.navigateUp()
            } else {
                Toast.makeText(requireActivity(), "등록 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
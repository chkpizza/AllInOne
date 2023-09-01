package com.wantique.daily.ui.record

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.wantique.base.ui.BaseFragment
import com.wantique.daily.R
import com.wantique.daily.databinding.FragmentWriteRecordBinding
import com.wantique.daily.di.DailyComponentProvider
import javax.inject.Inject

class WriteRecordFragment : BaseFragment<FragmentWriteRecordBinding>(R.layout.fragment_write_record) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[RecordViewModel::class.java]}

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

        binding.lifecycleOwner = this
        binding.vm = viewModel

        updateInsets()
        setUpViewListener()
        setUpObserver()
    }

    private fun setUpViewListener() {
        binding.writeRecordContainerAddImage.setOnClickListener {
            galleryLauncher.launch(Intent().also {
                it.type = "image/*"
                it.action = Intent.ACTION_GET_CONTENT
            })
        }

        binding.writeRecordIvRemoveImage.setOnClickListener {
            viewModel.setUri(null)
        }

        binding.writeRecordEtBody.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    binding.writeRecordBtnRegistration.isEnabled = it.length >= 7
                }
            }
        })

        binding.writeRecordBtnRegistration.setOnClickListener {
            viewModel.registrationRecord(binding.writeRecordEtBody.text.toString())
        }

        binding.writeRecordBtnRegistration.isEnabled = false
    }

    private fun setUpObserver() {
        viewModel.registration.asLiveData().observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireActivity(), "등록에 성공하였습니다\n검토 후 정상 업로드될 예정입니다", Toast.LENGTH_SHORT).show()
                navigator.navigateUp()
            }
        }
    }
}
package com.wantique.home.ui.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import com.wantique.base.state.isSuccessOrNull
import com.wantique.base.ui.BaseFragment
import com.wantique.home.R
import com.wantique.home.databinding.FragmentProfessorDetailsBinding
import com.wantique.home.di.HomeComponentProvider
import com.wantique.home.ui.details.adapter.CurriculumAdapter
import javax.inject.Inject

class ProfessorDetailsFragment : BaseFragment<FragmentProfessorDetailsBinding>(R.layout.fragment_professor_details) {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[ProfessorDetailsViewModel::class.java]}
    private val navArgs: ProfessorDetailsFragmentArgs by navArgs()
    private val id by lazy { navArgs.id }
    private lateinit var curriculumAdapter: CurriculumAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as HomeComponentProvider).getHomeComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = viewModel


        updateInsets()
        setUpRecyclerView()
        setUpViewListener()
        setUpObservers()
        request()
     }

    private fun setUpRecyclerView() {
        curriculumAdapter = CurriculumAdapter()
        binding.professorDetailsRvCurriculum.adapter = curriculumAdapter
    }

    private fun setUpViewListener() {
        binding.professorDetailsToolbar.setNavigationOnClickListener {
            navigator.navigateUp()
        }

        binding.professorDetailsLayoutError.networkErrorBtnRetry.setOnClickListener {
            request()
        }
    }


    private fun setUpObservers() {
        viewModel.curriculum.asLiveData().observe(viewLifecycleOwner) {
            it.isSuccessOrNull()?.let {
                binding.professorDetailsTvCurriculumLink.setOnClickListener { _ ->
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
                }
            }
        }

        viewModel.professorInfo.asLiveData().observe(viewLifecycleOwner) {
            it.isSuccessOrNull()?.let {

            }
        }
    }

    private fun request() {
        viewModel.getProfessorDetails(id)
    }
}
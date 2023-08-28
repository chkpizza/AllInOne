package com.wantique.home.ui.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.wantique.base.ui.BaseFragment
import com.wantique.home.R
import com.wantique.home.databinding.FragmentProfessorDetailsBinding
import com.wantique.home.di.HomeComponentProvider
import javax.inject.Inject

class ProfessorDetailsFragment : BaseFragment<FragmentProfessorDetailsBinding>(R.layout.fragment_professor_details) {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy { ViewModelProvider(this, factory)[ProfessorDetailsViewModel::class.java]}

    private val navArgs: ProfessorDetailsFragmentArgs by navArgs()
    private val id by lazy { navArgs.id }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as HomeComponentProvider).getHomeComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateInsets()
        setUpViewListener()

        binding.professorDetailsTvToolbarTitle.text = id
    }

    private fun setUpViewListener() {
        binding.professorDetailsToolbar.setNavigationOnClickListener {
            navigator.navigateUp()
        }
    }
}
package com.wantique.home.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.wantique.base.ui.BaseFragment
import com.wantique.home.R
import com.wantique.home.databinding.FragmentHomeBinding
import com.wantique.home.ui.home.adapter.HomeAdapter
import com.wantique.home.di.HomeComponentProvider
import com.wantique.home.domain.model.ProfessorItem
import com.wantique.home.ui.home.adapter.listener.OnCategoryClickListener
import com.wantique.home.ui.home.adapter.listener.OnProfessorClickListener
import javax.inject.Inject


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    //private val viewModel by navGraphViewModels<HomeViewModel>(R.id.home_nav_graph) { factory }
    private val viewModel by lazy { ViewModelProvider(this, factory)[HomeViewModel::class.java]}
    private var clickTime: Long = 0

    private lateinit var homeAdapter: HomeAdapter

    override fun handleOnBackPressed() {
        if(System.currentTimeMillis() - clickTime >= 3500) {
            clickTime = System.currentTimeMillis()
            Toast.makeText(requireActivity(), "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            requireActivity().finish()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as HomeComponentProvider).getHomeComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.vm = viewModel

        updateTopInsets()
        setUpRecyclerView()
        setUpViewListener()
    }

    private fun setUpRecyclerView() {
        val onCategoryClickListener = object : OnCategoryClickListener {
            override fun onClick(position: Int) {
                viewModel.updateCategoryPosition(position)
            }
        }

        val onProfessorClickListener = object : OnProfessorClickListener {
            override fun onClick(professor: ProfessorItem) {
                navigator.navigate(HomeFragmentDirections.actionHomeFragmentToProfessorDetailsFragment(professor.id))
            }
        }

        homeAdapter = HomeAdapter(onCategoryClickListener, onProfessorClickListener)
        binding.homeRvContent.adapter = homeAdapter
        viewModel.fetchHome()
        Log.d("CurrentDestination", findNavController().currentBackStack.value.toString())
    }

    private fun setUpViewListener() {
        binding.homeLayoutError.networkErrorBtnRetry.setOnClickListener {
            viewModel.fetchHome()
        }
    }
}
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.wantique.base.navigation.NavigatorProvider
import com.wantique.base.state.UiState
import com.wantique.base.ui.BaseFragment
import com.wantique.firebase.Firebase
import com.wantique.home.R
import com.wantique.home.databinding.FragmentHomeBinding
import com.wantique.home.ui.home.adapter.HomeAdapter
import com.wantique.home.di.HomeComponentProvider
import com.wantique.home.domain.model.ProfessorItem
import com.wantique.home.ui.home.adapter.listener.OnCategoryClickListener
import com.wantique.home.ui.home.adapter.listener.OnProfessorClickListener
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    //private val viewModel by navGraphViewModels<HomeViewModel>(R.id.home_nav_graph) { factory }
    private val viewModel by lazy { ViewModelProvider(this, factory)[HomeViewModel::class.java]}
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private var clickTime: Long = 0

    private lateinit var homeAdapter: HomeAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as HomeComponentProvider).getHomeComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(System.currentTimeMillis() - clickTime >= 3500) {
                    clickTime = System.currentTimeMillis()
                    Toast.makeText(requireActivity(), "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
                } else {
                    requireActivity().finish()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        updateTopInsets()
        setUpRecyclerView()
        setUpViewListener()
        request()
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
    }

    private fun setUpViewListener() {
        binding.homeRefresh.setOnRefreshListener {
            if(viewModel.home.value is UiState.Success) {
                binding.homeRefresh.isRefreshing = false
            } else {
                request()
            }
        }

        binding.homeToolbar.setOnClickListener {
            navigator.navigate(HomeFragmentDirections.actionHomeFragmentToProfessorDetailsFragment(""))
        }
    }

    private fun request() {
        viewModel.fetchHome()
    }

    private fun setNotice() {
        lifecycleScope.launch {
            Firebase.getInstance().setNotice()
        }
    }

    private fun getNotice() {
        lifecycleScope.launch {
            //Firebase.getInstance().getNotice()
            Firebase.getInstance().getNotice()
        }
    }
}
package com.wantique.home.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navGraphViewModels
import com.wantique.base.state.getValue
import com.wantique.base.state.isSuccessOrNull
import com.wantique.base.ui.BaseFragment
import com.wantique.firebase.FireStore
import com.wantique.home.R
import com.wantique.home.databinding.FragmentHomeBinding
import com.wantique.home.domain.model.BannerItem
import com.wantique.home.domain.model.Home
import com.wantique.home.ui.adapter.HomeAdapter
import com.wantique.home.di.HomeComponentProvider
import com.wantique.home.domain.model.ProfessorItem
import com.wantique.home.ui.adapter.listener.OnCategoryClickListener
import com.wantique.home.ui.adapter.listener.OnProfessorClickListener
import com.wantique.home.ui.vm.HomeViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    @Inject lateinit var factory: ViewModelProvider.Factory
    private val viewModel by navGraphViewModels<HomeViewModel>(R.id.home_nav_graph) { factory }

    private var clickTime: Long = 0
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    private lateinit var homeAdapter: HomeAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as HomeComponentProvider).getHomeComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        clickTime = 0
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
                viewModel.updateCategory(position)
            }
        }

        val onProfessorClickListener = object : OnProfessorClickListener {
            override fun onClick(professor: ProfessorItem) {
                Toast.makeText(requireActivity(), "${professor.belong} 소속 ${professor.name} 교수님", Toast.LENGTH_SHORT).show()
            }

        }

        homeAdapter = HomeAdapter(onCategoryClickListener, onProfessorClickListener)
        binding.homeRvContent.adapter = homeAdapter
        viewModel.fetchHome()
    }

    private fun setUpViewListener() {
        binding.homeLayoutError.networkErrorBtnRetry.setOnClickListener {
            viewModel.fetchHome()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }
}
package com.wantique.home.ui.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import com.wantique.base.state.getError
import com.wantique.base.state.getValue
import com.wantique.base.state.isSuccessOrNull
import com.wantique.base.ui.BaseViewModel
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.usecase.GetBannerUseCase
import com.wantique.home.domain.usecase.GetCategoryUseCase
import com.wantique.home.domain.usecase.GetProfessorsUseCase
import com.wantique.home.domain.usecase.GetYearlyExamPlanUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getBannerUseCase: GetBannerUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getProfessorsUseCase: GetProfessorsUseCase,
    private val getYearlyExamPlanUseCase: GetYearlyExamPlanUseCase,
    networkTracker: NetworkTracker,
    context: Context
) : BaseViewModel(networkTracker, context) {
    private val _home = MutableStateFlow<UiState<List<Home>>>(UiState.Initialize)
    val home = _home.asStateFlow()

    private val _currentCategoryPosition = MutableStateFlow<UiState<Int>>(UiState.Initialize)
    val currentCategoryPosition = _currentCategoryPosition.asStateFlow()

    private val _professorsState = MutableStateFlow<UiState<List<Home.Professor>>>(UiState.Initialize)
    val professorsState = _professorsState.asStateFlow()

    fun fetchHome() {
        if(_home.value !is UiState.Success) {
            viewModelScope.launch {
                combine(getBanner(), getCategory(), getProfessors(), getYearlyExam()) { banner, category, professors, exam ->
                    when {
                        banner is UiState.Success && category is UiState.Success && professors is UiState.Success && exam is UiState.Success -> {
                            _professorsState.value = UiState.Success(professors.getValue())
                            _currentCategoryPosition.value = UiState.Success(0)
                            UiState.Success(listOf(banner.getValue(), category.getValue(), professors.getValue()[0], exam.getValue()))
                        }

                        banner is UiState.Error -> {
                            _errorState.value = banner.getError()
                            null
                        }

                        category is UiState.Error -> {
                            _errorState.value = category.getError()
                            null
                        }

                        professors is UiState.Error -> {
                            _errorState.value = professors.getError()
                            null
                        }

                        exam is UiState.Error -> {
                            _errorState.value = exam.getError()
                            null
                        }
                        else -> null
                    }
                }.collect { state ->
                    state?.let { _home.value = it }
                }
            }
        }
    }
    private fun getBanner() = safeFlow { getBannerUseCase() }
    private fun getCategory() = safeFlow { getCategoryUseCase() }
    private fun getProfessors() = safeFlow { getProfessorsUseCase() }
    private fun getYearlyExam() = safeFlow { getYearlyExamPlanUseCase() }

    fun updateCategoryPosition(position: Int) {
        _currentCategoryPosition.value = UiState.Success(position)
        updateProfessors(position)
    }

    private fun updateProfessors(position: Int) {
        home.value.isSuccessOrNull()?.let {
            it.toMutableList().apply {
                forEachIndexed { index, home ->
                    if(home is Home.Professor) {
                        if(_professorsState.value.isSuccessOrNull() != null) {
                            set(index, _professorsState.value.getValue()[position])
                            _home.value = UiState.Success(this)
                        }
                    }
                }
            }
        }
    }
}
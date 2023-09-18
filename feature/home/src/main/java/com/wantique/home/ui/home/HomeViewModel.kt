package com.wantique.home.ui.home

import android.content.Context
import android.util.Log
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
import com.wantique.home.domain.usecase.GetNoticeUseCase
import com.wantique.home.domain.usecase.GetProfessorsUseCase
import com.wantique.home.domain.usecase.GetYearlyExamPlanUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getBannerUseCase: GetBannerUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getProfessorsUseCase: GetProfessorsUseCase,
    private val getYearlyExamPlanUseCase: GetYearlyExamPlanUseCase,
    private val getNoticeUseCase: GetNoticeUseCase,
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
                combine(getBanner(), getCategory(), getProfessors(), getYearlyExam(), getNotice()) { banner , category, professors, exam, notice ->
                    when {
                        banner is UiState.Success && category is UiState.Success && professors is UiState.Success && exam is UiState.Success && notice is UiState.Success -> {
                            _professorsState.value = UiState.Success(professors.getValue())
                            _currentCategoryPosition.value = UiState.Success(0)
                            _home.value = UiState.Success(listOf(banner.getValue(), category.getValue(), professors.getValue()[0], exam.getValue(), notice.getValue()))
                        }

                        banner is UiState.Error -> {
                            _errorState.value = banner.getError()
                        }

                        category is UiState.Error -> {
                            _errorState.value = category.getError()
                        }

                        professors is UiState.Error -> {
                            _errorState.value = professors.getError()
                        }

                        exam is UiState.Error -> {
                            _errorState.value = exam.getError()
                        }

                        notice is UiState.Error -> {
                            _errorState.value = notice.getError()
                        }
                    }
                }.collect()
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            combine(getBanner(), getCategory(), getProfessors(), getYearlyExam(), getNotice()) { banner , category, professors, exam, notice ->
                when {
                    banner is UiState.Success && category is UiState.Success && professors is UiState.Success && exam is UiState.Success && notice is UiState.Success -> {
                        _professorsState.value = UiState.Success(professors.getValue())
                        _currentCategoryPosition.value = UiState.Success(0)
                        _home.value = UiState.Success(listOf(banner.getValue(), category.getValue(), professors.getValue()[0], exam.getValue(), notice.getValue()))
                    }

                    banner is UiState.Error -> {
                        _errorState.value = banner.getError()
                    }

                    category is UiState.Error -> {
                        _errorState.value = category.getError()
                    }

                    professors is UiState.Error -> {
                        _errorState.value = professors.getError()
                    }

                    exam is UiState.Error -> {
                        _errorState.value = exam.getError()
                    }

                    notice is UiState.Error -> {
                        _errorState.value = notice.getError()
                    }
                }
            }.collect()
        }
    }


    private fun getBanner() = safeFlow { getBannerUseCase() }
    private fun getCategory() = safeFlow { getCategoryUseCase() }
    private fun getProfessors() = safeFlow { getProfessorsUseCase() }
    private fun getYearlyExam() = safeFlow { getYearlyExamPlanUseCase() }
    private fun getNotice() = safeFlow { getNoticeUseCase() }

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
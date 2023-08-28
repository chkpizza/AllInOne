package com.wantique.home.ui.details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import com.wantique.base.state.getError
import com.wantique.base.state.getValue
import com.wantique.base.state.isErrorOrNull
import com.wantique.base.state.isSuccessOrNull
import com.wantique.base.ui.BaseViewModel
import com.wantique.home.domain.model.ProfessorInfo
import com.wantique.home.domain.model.YearlyCurriculum
import com.wantique.home.domain.usecase.GetProfessorCurriculumUseCase
import com.wantique.home.domain.usecase.GetProfessorInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfessorDetailsViewModel @Inject constructor(
    private val getProfessorCurriculumUseCase: GetProfessorCurriculumUseCase,
    private val getProfessorInfoUseCase: GetProfessorInfoUseCase,
    networkTracker: NetworkTracker,
    context: Context
) : BaseViewModel(networkTracker, context) {
    private val _curriculum = MutableStateFlow<UiState<YearlyCurriculum>>(UiState.Loading)
    val curriculum = _curriculum.asStateFlow()

    private val _professorInfo = MutableStateFlow<UiState<ProfessorInfo>>(UiState.Loading)
    val professorInfo = _professorInfo.asStateFlow()

    fun getProfessorDetails(professorId: String) {
        getProfessorInfo(professorId)
        getProfessorCurriculum(professorId)
    }

    private fun getProfessorCurriculum(professorId: String) {
        viewModelScope.launch {
            safeFlow {
                getProfessorCurriculumUseCase(professorId)
            }.onEach {
                it.isErrorOrNull()?.let { e ->
                    _errorState.value = e
                } ?: run {
                    _curriculum.value = it
                }
            }.collect()
        }
    }

    private fun getProfessorInfo(professorId: String) {
        viewModelScope.launch {
            safeFlow {
                getProfessorInfoUseCase(professorId)
            }.onEach {
                it.isErrorOrNull()?.let { e ->
                    _errorState.value = e
                } ?: run {
                    _professorInfo.value = it
                }
            }.collect()
        }
    }
}
package com.wantique.daily.ui.daily

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import com.wantique.base.state.getError
import com.wantique.base.state.getValue
import com.wantique.base.ui.BaseViewModel
import com.wantique.daily.domain.model.Daily
import com.wantique.daily.domain.usecase.GetDailyLetterUseCase
import com.wantique.daily.domain.usecase.GetDailyPastExamUseCase
import com.wantique.daily.domain.usecase.GetDailyRecordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class DailyViewModel @Inject constructor(
    networkTracker: NetworkTracker,
    context: Context,
    private val getDailyLetterUseCase: GetDailyLetterUseCase,
    private val getDailyRecordUseCase: GetDailyRecordUseCase,
    private val getDailyPastExamUseCase: GetDailyPastExamUseCase
) : BaseViewModel(networkTracker, context) {
    private val _daily = MutableStateFlow<UiState<List<Daily>>>(UiState.Initialize)
    val daily = _daily.asStateFlow()

    fun fetchDaily() {
        viewModelScope.launch {
            combine(getDailyLetter(), getDailyRecord(), getDailyPastExam()) { letter, record, pastExam ->
                when {
                    letter is UiState.Success && record is UiState.Success && pastExam is UiState.Success -> {
                        _daily.value = UiState.Success(listOf(letter.getValue(), record.getValue(), pastExam.getValue()))
                    }
                    letter is UiState.Error -> {
                        _errorState.value = letter.getError()
                    }
                    record is UiState.Error -> {
                        _errorState.value = record.getError()
                    }
                    pastExam is UiState.Error -> {
                        Log.d("pastExamErrorTest", "error")
                        _errorState.value = pastExam.getError()
                    }
                }
            }.collect()
        }
    }

    private fun getDailyLetter() = safeFlow { getDailyLetterUseCase() }
    private fun getDailyRecord() = safeFlow { getDailyRecordUseCase() }
    private fun getDailyPastExam() = safeFlow { getDailyPastExamUseCase() }
}
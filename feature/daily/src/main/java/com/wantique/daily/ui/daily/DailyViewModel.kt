package com.wantique.daily.ui.daily

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import com.wantique.base.state.getError
import com.wantique.base.state.getValue
import com.wantique.base.state.isErrorOrNull
import com.wantique.base.ui.BaseViewModel
import com.wantique.daily.domain.model.Daily
import com.wantique.daily.domain.usecase.GetDailyLetterUseCase
import com.wantique.daily.domain.usecase.GetDailyPromiseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class DailyViewModel @Inject constructor(
    private val getDailyLetterUseCase: GetDailyLetterUseCase,
    private val getDailyPromiseUseCase: GetDailyPromiseUseCase,
    networkTracker: NetworkTracker,
    context: Context
) : BaseViewModel(networkTracker, context) {

    private val _daily = MutableStateFlow<UiState<List<Daily>>>(UiState.Initialize)
    val daily = _daily.asStateFlow()

    fun fetchDaily() {
        /*
        viewModelScope.launch {
            safeFlow {
                getDailyPromiseUseCase()
            }.onEach {
                it.isErrorOrNull()?.let { e ->
                    Log.d("DailyPromise", e.toString())
                    _errorState.value = e
                } ?: run {
                    Log.d("DailyPromise", it.getValue().toString())
                    _daily.value = UiState.Success(listOf<Daily>(it.getValue()))
                }
            }.collect()
        }

         */

        viewModelScope.launch {
            combine(getDailyLetter(), getDailyPromise()) { dailyLetter, dailyPromise ->
                when {
                    dailyLetter is UiState.Success && dailyPromise is UiState.Success -> {
                        UiState.Success(listOf(dailyLetter.getValue(), dailyPromise.getValue()))
                    }
                    dailyLetter is UiState.Error -> {
                        Log.d("dailyTest", dailyLetter.getError().toString())
                        _errorState.value = dailyLetter.getError()
                        null
                    }
                    dailyPromise is UiState.Error -> {
                        Log.d("dailyTest", dailyPromise.getError().toString())
                        _errorState.value = dailyPromise.getError()
                        null
                    }
                    else -> null
                }
            }.collect { state ->
                state?.let {
                    _daily.value = it
                }
            }
        }
    }

    private fun getDailyLetter() = safeFlow { getDailyLetterUseCase() }
    private fun getDailyPromise() = safeFlow { getDailyPromiseUseCase() }
}
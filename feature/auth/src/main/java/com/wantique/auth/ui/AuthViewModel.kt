package com.wantique.auth.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wantique.auth.domain.usecase.IsExistUserUseCase
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import com.wantique.base.state.getValue
import com.wantique.base.state.isErrorOrNull
import com.wantique.base.ui.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val isExistUserUseCase: IsExistUserUseCase,
    networkTracker: NetworkTracker,
    context: Context
) : BaseViewModel(networkTracker, context) {
    private val _navigateToHome = MutableSharedFlow<Boolean>()
    val navigateToHome = _navigateToHome.asSharedFlow()

    private val _timer = MutableStateFlow<UiState<Int>>(UiState.Initialize)
    val timer = _timer.asStateFlow()

    private lateinit var timerJob: Job

    fun startTimer(limit: Int) {
        if (::timerJob.isInitialized) {
            timerJob.cancel()
        }

        timerJob = viewModelScope.launch {
            var remain = limit
            while (remain > 0) {
                _timer.value = UiState.Success(--remain)
                delay(1000)
            }
        }
    }

    fun isExistUser() {
        viewModelScope.launch {
            safeCall {
                isExistUserUseCase()
            }.onEach {
                it.isErrorOrNull()?.let { throwable ->
                    _errorState.value = throwable
                } ?: run {
                    _navigateToHome.emit(it.getValue())
                }
            }.collect()
        }
    }
}
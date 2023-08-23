package com.wantique.auth.ui.vm

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.wantique.auth.domain.usecase.IsExistUserUseCase
import com.wantique.auth.domain.usecase.IsWithdrawalUserUseCase
import com.wantique.auth.domain.usecase.RedoUserUseCase
import com.wantique.auth.domain.usecase.RegisterUserUseCase
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
    private val isWithdrawalUserUseCase: IsWithdrawalUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val redoUserUseCase: RedoUserUseCase,
    networkTracker: NetworkTracker,
    context: Context
) : BaseViewModel(networkTracker, context) {
    private val _exist = MutableSharedFlow<Boolean>()
    val exist = _exist.asSharedFlow()

    private val _withdrawal = MutableSharedFlow<Boolean>()
    val withdrawal = _withdrawal.asSharedFlow()

    private val _registration = MutableSharedFlow<Boolean>()
    val registration = _registration.asSharedFlow()

    private val _redo = MutableSharedFlow<Boolean>()
    val redo = _redo.asSharedFlow()

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
            safeFlow {
                isExistUserUseCase()
            }.onEach {
                it.isErrorOrNull()?.let { throwable ->
                    _errorState.emit(throwable)
                } ?: run {
                    _exist.emit(it.getValue())
                }
            }.collect()
        }
    }

    fun isWithdrawalUser() {
        viewModelScope.launch {
            safeFlow {
                isWithdrawalUserUseCase()
            }.onEach {
                it.isErrorOrNull()?.let { throwable ->
                    _errorState.emit(throwable)
                } ?: run {
                    _withdrawal.emit(it.getValue())
                }
            }.collect()
        }
    }

    fun registerUser() {
        viewModelScope.launch {
            safeFlow {
                registerUserUseCase()
            }.onEach {
                it.isErrorOrNull()?.let { throwable ->
                    _errorState.emit(throwable)
                } ?: run {
                    _registration.emit(it.getValue())
                }
            }.collect()
        }
    }

    fun redoUser() {
        viewModelScope.launch {
            safeFlow {
                redoUserUseCase()
            }.onEach {
                it.isErrorOrNull()?.let { throwable ->
                    _errorState.emit(throwable)
                } ?: run {
                    _redo.emit(it.getValue())
                }
            }.collect()
        }
    }

    fun testOuter() {
        viewModelScope.launch {
            val home = mutableListOf<Boolean>()

            test().also {
                it.isErrorOrNull()?.let { throwable ->
                    _errorState.emit(throwable)
                } ?: run {
                    home.add(it.getValue())
                }
            }
        }
    }

    private suspend fun test(): UiState<Boolean> {
        return safeCall {
            UiState.Success(true)
        }
    }
}
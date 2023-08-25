package com.wantique.auth.ui.vm

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.wantique.auth.domain.usecase.CheckDuplicateNickNameUseCase
import com.wantique.auth.domain.usecase.IsExistUserUseCase
import com.wantique.auth.domain.usecase.IsWithdrawalUserUseCase
import com.wantique.auth.domain.usecase.RedoUserUseCase
import com.wantique.auth.domain.usecase.RegisterUserUseCase
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import com.wantique.base.state.getValue
import com.wantique.base.state.isErrorOrNull
import com.wantique.base.state.isSuccessOrNull
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
    private val checkDuplicateNickNameUseCase: CheckDuplicateNickNameUseCase,
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

    private val _uri = MutableStateFlow<UiState<Uri>>(UiState.Initialize)
    val uri = _uri.asStateFlow()

    fun isExistUser() {
        viewModelScope.launch {
            safeFlow {
                isExistUserUseCase()
            }.onEach {
                it.isErrorOrNull()?.let { throwable ->
                    _errorState.emit(throwable)
                } ?: run {
                    Log.d("CallTest", "exist emit")
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

    /*
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

     */

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

    fun checkNickName(nickName: String) {
        viewModelScope.launch {
            safeFlow {
                checkDuplicateNickNameUseCase(nickName)
            }.onEach {
                it.isErrorOrNull()?.let { throwable ->
                    _errorState.emit(throwable)
                } ?: run {
                    if(it.getValue()) {
                        _errorState.emit(Throwable("중복된 닉네임입니다"))
                    } else {
                       registerUser(nickName)
                    }
                }
            }.collect()
        }
    }

    private fun registerUser(nickName: String) {
        viewModelScope.launch {
            val profileImageUri = uri.value.isSuccessOrNull()?.let {
                it.toString()
            } ?: run {
                ""
            }
            safeFlow {
                registerUserUseCase(profileImageUri, nickName)
            }.onEach {
                it.isErrorOrNull()?.let { throwable ->
                    _errorState.emit(throwable)
                } ?: run {
                    _registration.emit(it.getValue())
                }
            }.collect()
        }
    }


    fun setUri(uri: Uri) {
        _uri.value = UiState.Success(uri)
    }
}
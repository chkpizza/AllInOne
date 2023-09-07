package com.wantique.mypage.ui.edit

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import com.wantique.base.state.getValue
import com.wantique.base.state.isErrorOrNull
import com.wantique.base.state.isSuccessOrNull
import com.wantique.base.ui.BaseViewModel
import com.wantique.mypage.domain.model.UserProfile
import com.wantique.mypage.domain.usecase.GetUserProfileUseCase
import com.wantique.mypage.domain.usecase.IsDuplicateNickName
import com.wantique.mypage.domain.usecase.ModifyUserProfileUseCase
import com.wantique.mypage.domain.usecase.WithdrawalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditViewModel @Inject constructor(
    networkTracker: NetworkTracker,
    context: Context,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val isDuplicateNickName: IsDuplicateNickName,
    private val modifyUserProfileUseCase: ModifyUserProfileUseCase,
    private val withdrawalUseCase: WithdrawalUseCase
) : BaseViewModel(networkTracker, context) {
    private val _profile = MutableStateFlow<UiState<UserProfile>>(UiState.Initialize)
    val profile = _profile.asStateFlow()

    private var uri: Uri? = null

    private val _modify = MutableStateFlow<Boolean?>(null)
    val modify = _modify.asStateFlow()

    private val _withdrawal = MutableStateFlow<Boolean?>(null)
    val withdrawal = _withdrawal.asStateFlow()

    fun fetch() {
        viewModelScope.launch {
            safeFlow {
                getUserProfileUseCase()
            }.onEach {
                it.isErrorOrNull()?.let { e ->
                    _errorState.value = e
                } ?: run {
                    _profile.value = it
                }
            }.collect()
        }
    }

    fun setProfileUri(uri: Uri) {
        this.uri = uri
    }

    fun checkValidNickName(nickName: String) {
        viewModelScope.launch {
            safeFlow {
                isDuplicateNickName(nickName)
            }.onEach {
                it.isErrorOrNull()?.let { e ->
                    _errorState.value = e
                } ?: run {
                    if(it.getValue()) {
                        _errorState.value = Throwable("중복된 닉네임입니다")
                    } else {
                        modifyUserProfile(nickName)
                    }
                }
            }.collect()
        }
    }

    private fun modifyUserProfile(nickName: String) {
        viewModelScope.launch {
            val imageUri = uri?.toString() ?: ""
            safeFlow {
                modifyUserProfileUseCase(imageUri, nickName)
            }.onEach {
                it.isErrorOrNull()?.let { e ->
                    _errorState.value = e
                } ?: run {
                    _modify.value = it.getValue()
                }
            }.collect()
        }
    }

    fun withdrawalUser() {
        viewModelScope.launch {
            safeFlow {
                withdrawalUseCase()
            }.onEach {
                it.isErrorOrNull()?.let { e ->
                    _errorState.value = e
                } ?: run {
                    _withdrawal.value = it.getValue()
                }
            }.collect()
        }
    }
}
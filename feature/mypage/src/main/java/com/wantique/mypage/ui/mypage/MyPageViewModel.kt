package com.wantique.mypage.ui.mypage

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import com.wantique.base.state.isErrorOrNull
import com.wantique.base.ui.BaseViewModel
import com.wantique.mypage.domain.model.UserProfile
import com.wantique.mypage.domain.usecase.GetUserProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyPageViewModel @Inject constructor(
    networkTracker: NetworkTracker,
    context: Context,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : BaseViewModel(networkTracker, context) {
    private val _profile = MutableStateFlow<UiState<UserProfile>>(UiState.Initialize)
    val profile = _profile.asStateFlow()

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
}
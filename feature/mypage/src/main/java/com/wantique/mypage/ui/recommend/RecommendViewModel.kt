package com.wantique.mypage.ui.recommend

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import com.wantique.base.state.isErrorOrNull
import com.wantique.base.ui.BaseViewModel
import com.wantique.mypage.domain.usecase.RegisterRecommendUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecommendViewModel @Inject constructor(
    networkTracker: NetworkTracker,
    context: Context,
    private val registerRecommendUseCase: RegisterRecommendUseCase
) : BaseViewModel(networkTracker, context) {
    private val _recommend = MutableStateFlow<UiState<Boolean>>(UiState.Initialize)
    val recommend = _recommend.asStateFlow()

    fun registerRecommend(recommend: String) {
        viewModelScope.launch {
            safeFlow {
                registerRecommendUseCase(recommend)
            }.onEach {
                it.isErrorOrNull()?.let { e ->
                    _errorState.value = e
                } ?: run {
                    _recommend.value = it
                }
            }.collect()
        }
    }
}
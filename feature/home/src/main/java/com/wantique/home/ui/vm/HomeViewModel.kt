package com.wantique.home.ui.vm

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import com.wantique.base.state.getValue
import com.wantique.base.state.isErrorOrNull
import com.wantique.base.state.isSuccessOrNull
import com.wantique.base.ui.BaseViewModel
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.usecase.GetBannerUseCase
import com.wantique.home.domain.usecase.GetCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getBannerUseCase: GetBannerUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    networkTracker: NetworkTracker,
    context: Context
) : BaseViewModel(networkTracker, context) {
    private val _home = MutableStateFlow<UiState<List<Home>>>(UiState.Initialize)
    val home = _home.asStateFlow()

    private val _currentCategoryPosition = MutableStateFlow<UiState<Int>>(UiState.Initialize)
    val currentCategoryPosition = _currentCategoryPosition.asStateFlow()

    fun fetchHome() {
        if(home.value is UiState.Initialize) {
            viewModelScope.launch {
                mutableListOf<Home>().apply {
                    getBanner().also {
                        it.isErrorOrNull()?.let { e ->
                            _errorState.emit(e)
                        } ?: run {
                            add(it.getValue())
                        }
                    }

                    getCategory().also {
                        it.isErrorOrNull()?.let { e ->
                            _errorState.emit(e)
                        } ?: run {
                            add(it.getValue())
                        }
                    }

                    _home.value = UiState.Success(this)
                    _currentCategoryPosition.value = UiState.Success(0)
                }
            }
        }
    }

    private suspend fun getBanner(): UiState<Home.Banner> {
        return safeCall {
            getBannerUseCase()
        }
    }

    private suspend fun getCategory(): UiState<Home.Category> {
        return safeCall {
            getCategoryUseCase()
        }
    }

    fun updateCategory(position: Int) {
        _currentCategoryPosition.value = UiState.Success(position)
    }
}
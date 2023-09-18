package com.wantique.home.ui.notice

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import com.wantique.base.state.isErrorOrNull
import com.wantique.base.ui.BaseViewModel
import com.wantique.home.domain.model.NoticeItem
import com.wantique.home.domain.usecase.GetAllNoticeUseCase
import com.wantique.home.domain.usecase.GetNoticeByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoticeViewModel @Inject constructor(
    networkTracker: NetworkTracker,
    context: Context,
    private val getNoticeByIdUseCase: GetNoticeByIdUseCase,
    private val getAllNoticeUseCase: GetAllNoticeUseCase
) : BaseViewModel(networkTracker, context) {
    private val _notice = MutableStateFlow<UiState<NoticeItem>>(UiState.Initialize)
    val notice = _notice.asStateFlow()

    private val _notices = MutableStateFlow<UiState<List<NoticeItem>>>(UiState.Initialize)
    val notices = _notices.asStateFlow()

    fun fetchNotice(id: String) {
        viewModelScope.launch {
            safeFlow {
                getNoticeByIdUseCase(id)
            }.onEach {
                it.isErrorOrNull()?.let { e ->
                    _errorState.value = e
                } ?: run {
                    _notice.value = it
                }
            }.collect()
        }
    }

    fun fetchAllNotice() {
        viewModelScope.launch {
            safeFlow {
                getAllNoticeUseCase()
            }.onEach {
                it.isErrorOrNull()?.let { e ->
                    _errorState.value = e
                } ?: run {
                    _notices.value = it
                }
            }.collect()
        }
    }
}
package com.wantique.daily.ui.record

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
import com.wantique.daily.domain.usecase.RegisterRecordUseCase
import com.wantique.daily.domain.usecase.RemoveRecordUseCase
import com.wantique.daily.domain.usecase.ReportRecordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecordViewModel @Inject constructor(
    networkTracker: NetworkTracker,
    context: Context,
    private val registerRecordUseCase: RegisterRecordUseCase,
    private val reportRecordUseCase: ReportRecordUseCase,
    private val removeRecordUseCase: RemoveRecordUseCase
) : BaseViewModel(networkTracker, context) {
    private val _uri = MutableStateFlow<UiState<Uri>>(UiState.Initialize)
    val uri = _uri.asStateFlow()

    private val _registration = MutableStateFlow<Boolean?>(null)
    val registration = _registration.asStateFlow()

    private val _report = MutableStateFlow<Boolean?>(null)
    val report = _report.asStateFlow()

    private val _remove = MutableStateFlow<Boolean?>(null)
    val remove = _remove.asStateFlow()

    fun setUri(uri: Uri?) {
        uri?.let {
            _uri.value = UiState.Success(it)
        } ?: run {
            _uri.value = UiState.Initialize
        }
    }

    fun registrationRecord(body: String) {
        viewModelScope.launch {
            safeFlow {
                _uri.value.isSuccessOrNull()?.let {
                    registerRecordUseCase(imageUri = it.toString(), body = body)
                } ?: run {
                    registerRecordUseCase(body = body)
                }
            }.onEach {
                it.isErrorOrNull()?.let { e ->
                    _errorState.value = e
                } ?: run {
                    _registration.value = it.getValue()
                }
            }.collect()
        }
    }

    fun reportRecord(documentId: String, reason: String) {
        viewModelScope.launch {
            safeFlow {
                reportRecordUseCase(documentId, reason)
            }.onEach {
                it.isErrorOrNull()?.let { e ->
                    _errorState.value = e
                } ?: run {
                    _report.value = it.getValue()
                }
            }.collect()
        }
    }

    fun removeRecord(documentId: String) {
        viewModelScope.launch {
            safeFlow {
                removeRecordUseCase(documentId)
            }.onEach {
                it.isErrorOrNull()?.let { e ->
                    _errorState.value = e
                } ?: run {
                    _remove.value = it.getValue()
                }
            }.collect()
        }
    }
}
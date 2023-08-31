package com.wantique.daily.ui.promise

import android.content.Context
import android.net.Uri
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import com.wantique.base.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class PromiseViewModel @Inject constructor(
    networkTracker: NetworkTracker,
    context: Context
) : BaseViewModel(networkTracker, context) {
    private val _uri = MutableStateFlow<UiState<Uri>>(UiState.Initialize)
    val uri = _uri.asStateFlow()

    fun setUri(uri: Uri) {
        _uri.value = UiState.Success(uri)
    }
}
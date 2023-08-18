package com.wantique.base.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wantique.base.state.NetworkState
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


open class BaseViewModel(networkTracker: NetworkTracker, applicationContext: Context) : ViewModel() {
    protected val _errorState = MutableSharedFlow<Throwable>()
    val errorState = _errorState.asSharedFlow()

    private lateinit var networkState: NetworkState

    init {
        initializeNetworkState(applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

        viewModelScope.launch(Dispatchers.IO) {
            networkTracker.networkStatus
                .onEach {
                    networkState = it
                }.collect()
        }
    }

    @SuppressLint("MissingPermission")
    private fun initializeNetworkState(connectivityManager: ConnectivityManager) {
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
            networkState = if(it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) or it.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR)) {
                NetworkState.Available
            } else {
                NetworkState.UnAvailable
            }
        } ?: run {
            networkState = NetworkState.UnAvailable
        }
    }

    fun isNetworkAvailable(): Boolean = when(networkState) {
        is NetworkState.Available -> true
        is NetworkState.UnAvailable -> false
    }

    fun <T> safeCall(call: () -> Flow<UiState<T>>): Flow<UiState<T>> = flow {
        if (isNetworkAvailable()) {
            emitAll(call())
        } else {
            emit(UiState.Error(Throwable("네트워크 연결 상태를 확인해 주세요")))
        }
    }
}
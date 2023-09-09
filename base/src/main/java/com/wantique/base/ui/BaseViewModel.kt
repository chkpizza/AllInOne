package com.wantique.base.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wantique.base.state.NetworkState
import com.wantique.base.network.NetworkTracker
import com.wantique.base.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.experimental.ExperimentalTypeInference


open class BaseViewModel(networkTracker: NetworkTracker, applicationContext: Context) : ViewModel() {
    protected val _errorState = MutableStateFlow<Throwable?>(null)
    val errorState = _errorState.asStateFlow()

    protected val _loadingState = MutableSharedFlow<Boolean>()
    val loadingState = _loadingState.stateIn(
        initialValue = false,
        started = SharingStarted.WhileSubscribed(5000),
        scope = viewModelScope
    )

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

    fun <T> safeFlow(call: () -> Flow<UiState<T>>): Flow<UiState<T>> = flow {
        _loadingState.emit(true)

        if (isNetworkAvailable()) {
            _errorState.value = null
            emitAll(call())
        } else {
            emit(UiState.Error(Throwable("NETWORK_CONNECTION_ERROR")))
        }

        _loadingState.emit(false)
    }

    suspend fun <T> safeCall(call: suspend  () -> UiState<T>): UiState<T> {
        _loadingState.emit(true)

        val state = if(isNetworkAvailable()) {
            _errorState.value = null
            call()
        } else {
            UiState.Error(Throwable("NETWORK_CONNECTION_ERROR"))
        }

        _loadingState.emit(false)

        return state
    }
}
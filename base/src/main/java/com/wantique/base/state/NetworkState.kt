package com.wantique.base.state

sealed class NetworkState {
    object Available : NetworkState()
    object UnAvailable : NetworkState()
}
package com.wantique.auth

interface WebClientIdProvider {
    fun getWebClientId(): String
}
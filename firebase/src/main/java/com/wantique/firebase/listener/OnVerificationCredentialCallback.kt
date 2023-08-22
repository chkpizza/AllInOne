package com.wantique.firebase.listener

interface OnVerificationCredentialCallback {
    fun onSuccess()
    fun onFailure(message: String)
}
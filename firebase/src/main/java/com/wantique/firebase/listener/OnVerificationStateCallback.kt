package com.wantique.firebase.listener

interface OnVerificationStateCallback {
    fun onVerificationFailed(message: String)
    fun onCodeSent(verificationId: String)
}
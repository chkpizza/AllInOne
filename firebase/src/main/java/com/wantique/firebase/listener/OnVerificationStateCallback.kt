package com.wantique.firebase.listener

interface OnVerificationStateCallback {
    fun onVerificationCompleted()
    fun onVerificationFailed()
    fun onCodeSent(verificationId: String)
}
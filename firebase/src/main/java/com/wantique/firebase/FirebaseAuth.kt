package com.wantique.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wantique.firebase.listener.OnVerificationCodeCallback
import com.wantique.firebase.listener.OnVerificationStateCallback
import java.util.concurrent.TimeUnit

class FirebaseAuth private constructor() {

    fun isSignIn(): Boolean = Firebase.auth.currentUser?.let {
        true
    } ?: run {
        false
    }

    fun verifyPhoneNumber(activity: Activity, phoneNumber: String, callback: OnVerificationStateCallback) {
        val callback = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                callback.onVerificationCompleted()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                callback.onVerificationFailed()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                callback.onCodeSent(verificationId)
            }
        }

        Firebase.auth.setLanguageCode("ko")

        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(120L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callback)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(verificationId: String, code: String, callback: OnVerificationCodeCallback) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        Firebase.auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                callback.onSuccess()
            } else {
                callback.onFailure()
            }
        }
    }

    companion object {
        private lateinit var firebaseAuth: FirebaseAuth

        fun getInstance(): FirebaseAuth {
            if(!::firebaseAuth.isInitialized) {
                firebaseAuth = FirebaseAuth()
            }

            return firebaseAuth
        }
    }
}
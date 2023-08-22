package com.wantique.firebase

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wantique.firebase.listener.OnVerificationCredentialCallback
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
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

            override fun onVerificationFailed(e: FirebaseException) {
                if(e is FirebaseTooManyRequestsException) {
                    callback.onVerificationFailed("요청할 수 있는 횟수를 초과하였습니다. 잠시 후 다시 시도해주세요!")
                } else {
                    callback.onVerificationFailed("올바른 전화번호를 입력해주세요")
                }
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                callback.onCodeSent(verificationId)
            }
        }

        Firebase.auth.setLanguageCode("ko")
        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(convertFormat(phoneNumber))
            .setTimeout(120L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callback)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(verificationId: String, code: String, callback: OnVerificationCredentialCallback) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        Firebase.auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                callback.onSuccess()
            } else {
                callback.onFailure(task.exception?.message.toString())
            }
        }
    }

    private fun convertFormat(phoneNumber: String): String {
        val firstNumber : String = phoneNumber.substring(0,3)
        var lastNumber = phoneNumber.substring(3)

        when(firstNumber){
            "010" -> lastNumber = "+8210$lastNumber"
            "011" -> lastNumber = "+8211$lastNumber"
            "016" -> lastNumber = "+8216$lastNumber"
            "017" -> lastNumber = "+8217$lastNumber"
            "018" -> lastNumber = "+8218$lastNumber"
            "019" -> lastNumber = "+8219$lastNumber"
            "106" -> lastNumber = "+82106$lastNumber"
        }
        return lastNumber
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
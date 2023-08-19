package com.wantique.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuth private constructor() {

    fun isSignIn(): Boolean = Firebase.auth.currentUser?.let {
        true
    } ?: run {
        false
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
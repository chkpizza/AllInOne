package com.wantique.auth.di

interface AuthComponentProvider {
    fun getAuthComponent(): AuthComponent
}
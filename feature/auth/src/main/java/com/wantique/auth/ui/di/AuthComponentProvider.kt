package com.wantique.auth.ui.di

interface AuthComponentProvider {
    fun getAuthComponent(): AuthComponent
}
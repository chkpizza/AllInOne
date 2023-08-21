package com.wantique.auth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor() : ViewModel() {
    private val _timer = MutableLiveData<Int>()
    val timer: LiveData<Int> get() = _timer

    private lateinit var timerJob: Job

    fun startTimer(limit: Int) {
        if(::timerJob.isInitialized) {
            timerJob.cancel()
        }

        timerJob = viewModelScope.launch {
            var remain = limit
            while(remain > 0) {
                _timer.value = --remain
                delay(1000)
            }
        }
    }
}
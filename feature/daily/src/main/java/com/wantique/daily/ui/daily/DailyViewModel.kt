package com.wantique.daily.ui.daily

import android.content.Context
import android.util.Log
import com.wantique.base.network.NetworkTracker
import com.wantique.base.ui.BaseViewModel
import javax.inject.Inject

class DailyViewModel @Inject constructor(
    networkTracker: NetworkTracker,
    context: Context
) : BaseViewModel(networkTracker, context) {
    fun test() {
        Log.d("InjectTest", "success")
    }

}
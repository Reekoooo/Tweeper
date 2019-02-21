package com.example.tweeper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tweeper.internal.LoginStatus

class MainViewModel :ViewModel() {

    var loginStateNormal = LoginStatus.NOT_STARTED


}
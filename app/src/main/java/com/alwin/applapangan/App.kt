package com.alwin.applapangan

import android.app.Application
import android.util.Log
import com.orhanobut.hawk.Hawk

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        Hawk.init(applicationContext)
            .setLogInterceptor { message -> if (BuildConfig.DEBUG) Log.d("Hawk", message) }
            .build()
    }
}
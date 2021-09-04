package com.gaur.zpmarket

import android.app.Application
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZPBaseApplication : Application(){
    init {
        MultiDex.install(this)
    }
}

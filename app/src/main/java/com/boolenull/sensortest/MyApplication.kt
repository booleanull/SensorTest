package com.boolenull.sensortest

import android.app.Application
import com.boolenull.sensortest.di.AppComponent
import com.boolenull.sensortest.di.AppModule
import com.boolenull.sensortest.di.DaggerAppComponent
import com.boolenull.sensortest.di.DataModule

class MyApplication: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .dataModule(DataModule())
            .build()
    }
}
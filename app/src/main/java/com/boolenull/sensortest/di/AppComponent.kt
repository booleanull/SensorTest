package com.boolenull.sensortest.di

import com.boolenull.sensortest.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}
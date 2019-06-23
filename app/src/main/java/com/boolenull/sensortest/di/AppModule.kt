package com.boolenull.sensortest.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Singleton
    @Provides
    internal fun provideContext(): Context {
        return context
    }
}
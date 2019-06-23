package com.boolenull.sensortest.di

import android.content.Context
import com.boolenull.sensortest.data.ISensorRepository
import com.boolenull.sensortest.data.SensorRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    internal fun provideSensorRepository(context: Context): ISensorRepository {
        return SensorRepository(context)
    }
}
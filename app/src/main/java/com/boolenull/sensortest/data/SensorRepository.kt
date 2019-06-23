package com.boolenull.sensortest.data

import android.hardware.SensorManager
import com.boolenull.sensortest.model.MySensor

class SensorRepository(val sensorManager: SensorManager): ISensorRepository {

    override fun getSensors(): List<MySensor> {
        return listOf()
    }
}

package com.boolenull.sensortest.utils

import android.hardware.Sensor
import android.hardware.SensorEventListener

interface MySensorEventListener: SensorEventListener {

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
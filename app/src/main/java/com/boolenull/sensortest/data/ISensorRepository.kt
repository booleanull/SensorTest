package com.boolenull.sensortest.data

import com.boolenull.sensortest.model.MySensor

interface ISensorRepository {

    val additionalSensorCount: Int

    fun getIdentifySensorsSize(): Int

    fun getSensors(): List<MySensor>
}
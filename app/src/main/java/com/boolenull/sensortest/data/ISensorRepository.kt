package com.boolenull.sensortest.data

import com.boolenull.sensortest.model.MySensor

interface ISensorRepository {

    fun getSensors(): List<MySensor>
}
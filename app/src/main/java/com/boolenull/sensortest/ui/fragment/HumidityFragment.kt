package com.boolenull.sensortest.ui.fragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boolenull.sensortest.R
import com.boolenull.sensortest.utils.MySensorEventListener
import com.boolenull.sensortest.utils.maxPoint
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_light.*
import kotlinx.android.synthetic.main.fragment_light.view.*


class HumidityFragment: Fragment(), MySensorEventListener {

    lateinit var sensorManager: SensorManager
    var sensor: Sensor? = null
    var points = mutableListOf<DataPoint>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_light, container, false)

        sensorManager = inflater.context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        return view
    }

    override fun onStart() {
        super.onStart()

        if (sensor != null) {
            sensorManager.registerListener((this as SensorEventListener), sensor, 3000)
        }
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (points.size > maxPoint) points.clear()
        event?.let {
            view!!.tv.text = getString(R.string.humi, it.values[0])
            points.add(DataPoint(points.size.toDouble(), it.values[0].toDouble()))
            val series = LineGraphSeries<DataPoint>(points.toTypedArray())
            graph.removeAllSeries()
            graph.addSeries(series)
        }
    }
}

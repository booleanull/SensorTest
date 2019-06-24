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
import kotlinx.android.synthetic.main.fragment_acceler.*
import kotlinx.android.synthetic.main.fragment_light.view.*

class ProxityFragment: Fragment(), MySensorEventListener {

    lateinit var sensorManager: SensorManager
    var sensor: Sensor? = null
    var point = mutableListOf<DataPoint>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_light, container, false)

        sensorManager = inflater.context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        return view
    }

    override fun onStart() {
        super.onStart()

        if (sensor != null) {
            sensorManager.registerListener((this as SensorEventListener), sensor, 10000000)
        }
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (point.size > maxPoint) {
            point.clear()
        }

        event?.let {
            view!!.tv.text = getString(R.string.proxity, it.values[0])

            point.add(DataPoint(point.size.toDouble(), it.values[0].toDouble()))
            val series = LineGraphSeries<DataPoint>(point.toTypedArray())

            graph.removeAllSeries()
            graph.addSeries(series)
        }
    }
}

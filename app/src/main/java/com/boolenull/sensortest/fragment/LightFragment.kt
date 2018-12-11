package com.boolenull.sensortest.fragment

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
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_light.*
import kotlinx.android.synthetic.main.fragment_light.view.*


class LightFragment : Fragment(), SensorEventListener {

    val max = 100

    lateinit var sensorManager: SensorManager
    var sensor: Sensor? = null
    var points = mutableListOf<DataPoint>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_light, container, false)

        sensorManager = inflater.context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
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
        if (points.size > max) points.clear()
        view!!.tv.text = getString(R.string.light) + " " + event!!.values[0]
        points.add(DataPoint(points.size.toDouble(), event.values[0].toDouble()))
        val series = LineGraphSeries<DataPoint>(points.toTypedArray())
        graph.removeAllSeries()
        graph.addSeries(series)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}

package com.boolenull.sensortest.fragment

import android.content.Context
import android.graphics.Color
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
import kotlinx.android.synthetic.main.fragment_acceler.*
import kotlinx.android.synthetic.main.fragment_acceler.view.*

class GravityFragment : Fragment(), SensorEventListener {

    val max = 100

    lateinit var sensorManager: SensorManager
    var sensor: Sensor? = null
    var pointx = mutableListOf<DataPoint>()
    var pointy = mutableListOf<DataPoint>()
    var pointz = mutableListOf<DataPoint>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_rotation, container, false)

        sensorManager = inflater.context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

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

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (pointx.size > max) {
            pointx.clear()
            pointy.clear()
            pointz.clear()
        }

        view!!.tvX.text = getString(R.string.X) + " " + event!!.values[0]
        view!!.tvY.text = getString(R.string.Y) + " " + event!!.values[1]
        view!!.tvZ.text = getString(R.string.Z) + " " + event!!.values[2]

        //val df = DecimalFormat("#.###")
        //df.roundingMode = RoundingMode.CEILING

        pointx.add(DataPoint(pointx.size.toDouble(), event.values[0].toDouble()))
        pointy.add(DataPoint(pointy.size.toDouble(), event.values[1].toDouble()))
        pointz.add(DataPoint(pointz.size.toDouble(), event.values[2].toDouble()))

        val seriesx = LineGraphSeries<DataPoint>(pointx.toTypedArray())
        val seriesy = LineGraphSeries<DataPoint>(pointy.toTypedArray())
        val seriesz = LineGraphSeries<DataPoint>(pointz.toTypedArray())

        graph.removeAllSeries()

        seriesx.color = Color.GREEN
        seriesy.color = Color.RED

        seriesx.title = "X"
        seriesy.title = "Y"
        seriesz.title = "Z"

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setTextSize(12f);

        graph.addSeries(seriesx)
        graph.addSeries(seriesy)
        graph.addSeries(seriesz)
    }
}

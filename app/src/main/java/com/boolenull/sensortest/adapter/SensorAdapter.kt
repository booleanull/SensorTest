package com.boolenull.sensortest.adapter

import android.hardware.Sensor
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boolenull.sensortest.ui.MainActivity
import com.boolenull.sensortest.R
import com.boolenull.sensortest.model.EnumSensor
import com.boolenull.sensortest.model.MySensor
import com.boolenull.sensortest.ui.fragment.*
import kotlinx.android.synthetic.main.layout_sensor.view.*

class SensorAdapter(private val layoutInflater: LayoutInflater, private val items: List<MySensor>) :
        RecyclerView.Adapter<SensorAdapter.SensorHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SensorHolder {
        val view: View = layoutInflater.inflate(R.layout.layout_sensor, p0, false)
        return SensorHolder(view)
    }

    override fun onBindViewHolder(holder: SensorHolder, p1: Int) {
        if (items[holder.adapterPosition].id in EnumSensor.values().map { it.id }) {
            holder.more.textSize = 14F
            holder.more.setTextColor(ContextCompat.getColor(layoutInflater.context, R.color.colorBlack))
            holder.card.foreground = null
        } else {
            setMoreView(holder)
            holder.card.setOnClickListener {
                items[holder.adapterPosition].open = !items[holder.adapterPosition].open
                setMoreView(holder)
            }
        }

        holder.title.text = items[holder.adapterPosition].title
        holder.text.text = items[holder.adapterPosition].text
        holder.more.text = items[holder.adapterPosition].more
        holder.image.setImageResource(items[holder.adapterPosition].image)

        val sensorFragment: Fragment?
        val fragmentManager: FragmentManager = (layoutInflater.context as MainActivity).supportFragmentManager

        val containerId = holder.container.id
        val oldFragment = fragmentManager.findFragmentById(containerId)
        if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit()
        }

        val newContainerId = 111 + (Math.random() * 9999).toInt()
        holder.container.setId(newContainerId)

        when (items[holder.adapterPosition].id) {
            Sensor.TYPE_LIGHT -> sensorFragment = LightFragment()
            Sensor.TYPE_MAGNETIC_FIELD -> sensorFragment = MagneticFragment()
            Sensor.TYPE_GYROSCOPE -> sensorFragment = GyroscopeFragment()
            Sensor.TYPE_ACCELEROMETER -> sensorFragment = AccelerFragment()
            Sensor.TYPE_PROXIMITY -> sensorFragment = ProxityFragment()
            Sensor.TYPE_GRAVITY -> sensorFragment = GravityFragment()
            Sensor.TYPE_ROTATION_VECTOR -> sensorFragment = RotationFragment()
            Sensor.TYPE_AMBIENT_TEMPERATURE -> sensorFragment = TempFragment()
            Sensor.TYPE_RELATIVE_HUMIDITY -> sensorFragment = HumidityFragment()
            else -> sensorFragment = null
        }

        if (sensorFragment != null)
            fragmentManager.beginTransaction().replace(newContainerId, sensorFragment).commit()
    }

    private fun setMoreView(holder: SensorHolder) {
        if (items[holder.adapterPosition].open) {
            holder.container.visibility = View.VISIBLE
            holder.ivMore.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp)
        } else {
            holder.container.visibility = View.GONE
            holder.ivMore.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SensorHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.tvTitle
        val text = view.tvText
        val more = view.tvMore
        val image = view.ivPic
        val container = view.container
        val card = view.cvSensor
        val ivMore = view.ivMore
    }
}
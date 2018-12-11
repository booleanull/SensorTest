package com.boolenull.sensortest.data

import android.hardware.Sensor
import com.boolenull.sensortest.R

data class MySensor(val id: Int, val title: String, var text: String, val more: String, val image: Int, var open: Boolean)

fun getMySensor(id: Int): MySensor? {
    when (id) {
        Sensor.TYPE_ACCELEROMETER -> return MySensor(Sensor.TYPE_ACCELEROMETER, "Акселерометр", "", "Датчик ускорения для трех осей измерения", R.drawable.ic_toll_black_24dp, false)
        Sensor.TYPE_AMBIENT_TEMPERATURE -> return MySensor(Sensor.TYPE_AMBIENT_TEMPERATURE, "Температура", "", "Датчик температуры окружающей среды", R.drawable.ic_whatshot_black_24dp, false)
        Sensor.TYPE_GRAVITY -> return MySensor(Sensor.TYPE_GRAVITY, "Гравитация", "", "Датчик гравитации", R.drawable.ic_arrow_downward_black_24dp, false)
        Sensor.TYPE_GYROSCOPE -> return MySensor(Sensor.TYPE_GYROSCOPE, "Гироскоп", "", "Датчик скорости вращения корпуса мобильного телефона", R.drawable.ic_my_location_black_24dp, false)
        Sensor.TYPE_LIGHT -> return MySensor(Sensor.TYPE_LIGHT, "Освещенность", "", "Датчик освещенности", R.drawable.ic_lightbulb_outline_black_24dp, false)
        Sensor.TYPE_MAGNETIC_FIELD -> return MySensor(Sensor.TYPE_MAGNETIC_FIELD, "Магнитное поле", "", "Датчик магнитного поля", R.drawable.ic_explore_black_24dp, false)
        Sensor.TYPE_PRESSURE -> return MySensor(Sensor.TYPE_PRESSURE, "Давление", "", "Датчик давления ", R.drawable.ic_av_timer_black_24dp, false)
        Sensor.TYPE_PROXIMITY -> return MySensor(Sensor.TYPE_PROXIMITY, "Расстояние до объекта", "", "Датчик для определения расстояния до объекта", R.drawable.ic_person_pin_circle_black_24dp, false)
        Sensor.TYPE_RELATIVE_HUMIDITY -> return MySensor(Sensor.TYPE_RELATIVE_HUMIDITY, "Относительная влажность", "", "Датчик относительной влажности", R.drawable.ic_wb_cloudy_black_24dp, false)
        Sensor.TYPE_ROTATION_VECTOR -> return MySensor(Sensor.TYPE_ROTATION_VECTOR, "Ротация", "", "Датчик ориентации в виде угла поворота и оси", R.drawable.ic_3d_rotation_black_24dp, false)
        else -> return null
    }
}
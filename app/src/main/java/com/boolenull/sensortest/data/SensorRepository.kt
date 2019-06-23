package com.boolenull.sensortest.data

import android.app.KeyguardManager
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.net.NetworkInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import com.boolenull.sensortest.R
import com.boolenull.sensortest.model.EnumSensor
import com.boolenull.sensortest.model.MySensor
import com.boolenull.sensortest.model.getMySensor

class SensorRepository(val context: Context): ISensorRepository {

    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    override var additionalSensorCount: Int = 2

    override fun getIdentifySensorsSize(): Int {
        return sensorManager.getSensorList(Sensor.TYPE_ALL).size + additionalSensorCount
    }

    override fun getSensors(): List<MySensor> {
        val list = mutableListOf<MySensor>()
        var mySensor: MySensor
        for (sensor: Sensor in sensorManager.getSensorList(Sensor.TYPE_ALL)) {
            if (getMySensor(sensor.type) != null) {
                mySensor = getMySensor(sensor.type)!!
                mySensor.text = sensor.name
                list.add(mySensor)
            }
        }

        list.add(0, getWifiSensor())

        val cameraSensor = getCameraSensor()
        cameraSensor?.let {
            list.add(1, it)
        }

        list.add(2, getFingerSensor())

        return listOf()
    }

    private fun getWifiSensor(): MySensor {
        val manager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        var wifiState = ""
        var wifiName = ""
        if (manager.isWifiEnabled) {
            val wifiInfo = manager.connectionInfo
            if (wifiInfo != null) {
                val state = WifiInfo.getDetailedStateOf(wifiInfo.supplicantState)
                if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                    wifiState = context.getString(R.string.wifion)
                    wifiName = context.getString(R.string.wifiname, wifiInfo.ssid)
                }
            } else {
                wifiState = context.getString(R.string.wifion)
                wifiName = context.getString(R.string.wifinameunind)
            }
        } else {
            wifiState = context.getString(R.string.wifioff)
            wifiName = context.getString(R.string.wifinameindef)
        }
        return MySensor(
                EnumSensor.WIFI.id,
                context.getString(R.string.wifi),
                wifiState,
                wifiName,
                R.drawable.ic_network_wifi_black_24dp
        )
    }

    private fun getCameraSensor(): MySensor? {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

        val cameraFront = getCameraId(cameraManager, false)
                ?: return null
        val cameraBack = getCameraId(cameraManager, true)
                ?: return null

        return MySensor(
                EnumSensor.PHOTO.id,
                context.getString(R.string.camera),
                context.getString(R.string.front, getPixelCamera(cameraManager, cameraFront)),
                context.getString(R.string.back, getPixelCamera(cameraManager, cameraBack)),
                R.drawable.ic_camera_alt_black_24dp
        )
    }

    private fun getCameraId(cManager: CameraManager, type: Boolean): String? {
        var cameraId: String
        var camera: Int
        var characteristics: CameraCharacteristics
        for (i in cManager.cameraIdList) {
            cameraId = i
            characteristics = cManager.getCameraCharacteristics(cameraId)
            camera = characteristics.get(CameraCharacteristics.LENS_FACING)
            if (camera == CameraCharacteristics.LENS_FACING_FRONT && !type) {
                return cameraId
            }
            if (camera == CameraCharacteristics.LENS_FACING_BACK && type) {
                return cameraId
            }
        }
        return null
    }

    private fun getPixelCamera(cameraManager: CameraManager, camera: String): Int {
        val characteristics = cameraManager.getCameraCharacteristics(camera)
        val size = characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE)
        return size.width * size.height / 1024000 + 1
    }

    private fun getFingerSensor(): MySensor {
        val fingerActive: String
        val finger: String
        if (FingerprintManagerCompat.from(context).isHardwareDetected) {
            additionalSensorCount++
            fingerActive = context.getString(R.string.fingeron)
            val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            finger = if (!keyguardManager.isKeyguardSecure) {
                context.getString(R.string.fingernotsave)
            } else {
                val fingerprintManager = FingerprintManagerCompat.from(context)
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    context.getString(R.string.fingernothave)
                } else {
                    context.getString(R.string.fingerready)
                }
            }
        } else {
            fingerActive = context.getString(R.string.fingernot)
            finger = context.getString(R.string.fingernotactive)
        }
        return MySensor(
                EnumSensor.FINGER.id,
                context.getString(R.string.finger),
                context.getString(R.string.fingerdescription, fingerActive),
                context.getString(R.string.fingerstate, finger),
                R.drawable.ic_fingerprint_black_24dp
        )
    }
}

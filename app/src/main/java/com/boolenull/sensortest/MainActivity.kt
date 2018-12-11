package com.boolenull.sensortest

import android.app.KeyguardManager
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.net.NetworkInfo.DetailedState
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.boolenull.sensortest.adapter.SensorAdapter
import com.boolenull.sensortest.data.MySensor
import com.boolenull.sensortest.data.getMySensor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var anotherSize = 2
    val sensors = mutableListOf<MySensor>()
    lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Get Sensors List and Refactor to Data Class
        getDefaultSensors()

        // Set Another Sensors
        // Wifi
        getWifiSensor()

        // Camera
        getCameraSensor()

        // Finger
        getFingerSensor()

        // Set Texts to CardView view
        tvTitle.text = android.os.Build.MODEL
        tvText.text = getString(R.string.find) + " " + (sensorManager.getSensorList(Sensor.TYPE_ALL).size + anotherSize)
        tvMore.text = getString(R.string.indef) + " " + (sensors.size + anotherSize)

        // Recycler View
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val sensorAdapter = SensorAdapter(LayoutInflater.from(this), sensors)
        recyclerView.adapter = sensorAdapter
    }

    private fun getCameraSensor() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val characteristics = cameraManager.getCameraCharacteristics(getFacingCameraId(cameraManager, false))
        val map = characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE)

        val characteristics1 = cameraManager.getCameraCharacteristics(getFacingCameraId(cameraManager, true))
        val map1 = characteristics1.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE)

        sensors.add(1, MySensor(1001, getString(R.string.camera), getString(R.string.front) + " " + map.width * map.height / 1024000 + 1, getString(R.string.back) + " " + map1.width * map1.height / 1024000 + 1, R.drawable.ic_camera_alt_black_24dp, false))
    }

    fun getFacingCameraId(cManager: CameraManager, type: Boolean): String? {
        var cameraId: String
        var camera: Int
        var characteristics: CameraCharacteristics
        for (i in cManager.cameraIdList) {
            cameraId = i
            characteristics = cManager.getCameraCharacteristics(cameraId)
            camera = characteristics.get(CameraCharacteristics.LENS_FACING);
            if (camera == CameraCharacteristics.LENS_FACING_FRONT && type == false) {
                return cameraId;
            }
            if (camera == CameraCharacteristics.LENS_FACING_BACK && type == true) {
                return cameraId;
            }
        }
        return null
    }

    private fun getFingerSensor() {
        val fingerActive: String
        val finger: String
        if (FingerprintManagerCompat.from(this).isHardwareDetected()) {
            anotherSize++
            fingerActive = getString(R.string.fingeron)
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            if (!keyguardManager.isKeyguardSecure) {
                finger = getString(R.string.fingernotsave)
            } else {
                val fingerprintManager = FingerprintManagerCompat.from(this)
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    finger = getString(R.string.fingernothave)
                } else {
                    finger = getString(R.string.fingerready)
                }
            }
        } else {
            fingerActive = getString(R.string.fingernot)
            finger = getString(R.string.fingernotactive)
        }
        sensors.add(2, MySensor(1002, getString(R.string.finger), getString(R.string.finger) + ": " + fingerActive, getString(R.string.fingerstate) + " " + finger, R.drawable.ic_fingerprint_black_24dp, false))
    }

    private fun getWifiSensor() {
        val manager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (manager.isWifiEnabled) {
            val wifiInfo = manager.connectionInfo
            if (wifiInfo != null) {
                val state = WifiInfo.getDetailedStateOf(wifiInfo.supplicantState)
                if (state == DetailedState.CONNECTED || state == DetailedState.OBTAINING_IPADDR) {
                    sensors.add(0, MySensor(1000, getString(R.string.wifi), getString(R.string.wifion), getString(R.string.wifiname) + " " + wifiInfo.ssid, R.drawable.ic_network_wifi_black_24dp, false))
                }
            } else {
                sensors.add(0, MySensor(1000, getString(R.string.wifi), getString(R.string.wifion), getString(R.string.wifinameunind), R.drawable.ic_network_wifi_black_24dp, false))

            }
        } else {
            sensors.add(0, MySensor(1000, getString(R.string.wifi), getString(R.string.wifioff), getString(R.string.wifinameindef), R.drawable.ic_network_wifi_black_24dp, false))
        }
    }

    private fun getDefaultSensors() {
        var mySensor: MySensor
        for (sensor: Sensor in sensorManager.getSensorList(Sensor.TYPE_ALL)) {
            if (getMySensor(sensor.type) != null) {
                mySensor = getMySensor(sensor.type)!!
                mySensor.text = sensor.name
                sensors.add(mySensor)
            }
        }
    }
}

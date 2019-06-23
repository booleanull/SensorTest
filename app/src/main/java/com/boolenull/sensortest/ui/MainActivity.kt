package com.boolenull.sensortest.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.boolenull.sensortest.R
import com.boolenull.sensortest.adapter.SensorAdapter
import com.boolenull.sensortest.data.ISensorRepository
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity: AppCompatActivity() {

    @Inject
    lateinit var sensorRepository: ISensorRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sensorList = sensorRepository.getSensors()
        tvTitle.text = android.os.Build.MODEL
        tvText.text = getString(R.string.find, sensorRepository.getIdentifySensorsSize())
        tvMore.text = getString(R.string.indef, sensorList.size)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val sensorAdapter = SensorAdapter(LayoutInflater.from(this), sensorList)
        recyclerView.adapter = sensorAdapter
    }
}

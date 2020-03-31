package com.example.tiltsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() , SensorEventListener{

    //Call if sensor accuracy changed
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }


    override fun onSensorChanged(event: SensorEvent?) {
        //Call if sensor value changed
        //values[0] : x value : up -10 ~ 0, down 0 ~ 10
        //values[1] : y value : left -10 ~ 0, right 0 ~ 10
        //values[2] : z not used
        event?.let{
            Log.d("MainActivity", "onSensorChanged: x: ${event.values[0]} , y: ${event.values[1]}, z: ${event.values[2]}")
        }
    }

    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}

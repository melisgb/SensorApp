package com.example.sensorapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.service.carrier.CarrierMessagingService
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_shake.*
import kotlin.math.abs
import kotlin.math.sqrt

class ShakeActivity : AppCompatActivity(), SensorEventListener {
    //Sensor event listener will provide a value everytime sensor changes
    var sensor : Sensor? = null
    var sensorManager : SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shake)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //executed when the precision of a sensor changes
    }


    var acelVal = SensorManager.GRAVITY_EARTH
    var acelLast = SensorManager.GRAVITY_EARTH
    var shake = 0.00f

    override fun onSensorChanged(event: SensorEvent?) {
        //executed everytime a change occurs in one of the sensors
        val x = event!!.values[0] //acceleration force in x-axis
        val y = event!!.values[1]
        val z = event!!.values[2]

        acelLast = acelVal
        acelVal = sqrt((x*x + y*y + z*z).toDouble()).toFloat()

        val delta = abs(acelVal - acelLast)
        shake = shake * 0.5f + delta
        Log.d("Shake Info", shake.toString())
        if(shake > 8){
            var vibrate = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrate.vibrate(200)
            Toast.makeText(applicationContext, "Vibration", Toast.LENGTH_SHORT).show()
            txtView_shakeMsg.setTextColor(getColor(R.color.colorAccent))
        }
        else {
            txtView_shakeMsg.setTextColor(getColor(R.color.colorPrimaryDark))
        }
    }
}

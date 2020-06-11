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


    var xOld = 0.0
    var yOld = 0.0
    var zOld = 0.0
    var threadShort = 300.0
    var oldTime = 0L

    override fun onSensorChanged(event: SensorEvent?) {
        //executed everytime a change occurs in one of the sensors
        val x = event!!.values[0] //acceleration force in x-axis
        val y = event!!.values[1]
        val z = event!!.values[2]
        val currentTime = System.currentTimeMillis()
        if(currentTime - oldTime > 100){
            val timeDiff = currentTime - oldTime
            val distanceDiff = x + y + z - xOld - yOld - zOld
            oldTime = currentTime

            //speed = distance / time
            val speed = Math.abs(distanceDiff) / timeDiff*10000
            Log.d("App Info", speed.toString())

            if(speed > threadShort){
                var vibrate = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrate.vibrate(500)
                Toast.makeText(this, "Vibration", Toast.LENGTH_SHORT).show()
            }
            xOld = x.toDouble()
            yOld = y.toDouble()
            zOld = z.toDouble()

        }
    }
}

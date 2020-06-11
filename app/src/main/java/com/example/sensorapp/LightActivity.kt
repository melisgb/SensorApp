package com.example.sensorapp

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_light.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class LightActivity : AppCompatActivity(), SensorEventListener {
    //Sensor event listener will provide a value everytime sensor changes
    var sensor : Sensor? = null
    var sensorManager : SensorManager? = null
    var mediaPlayer : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
        mediaPlayer!!.stop()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //TO DO
    }

    var isRunning = false
    override fun onSensorChanged(event: SensorEvent?) {
        Log.d("Sensor Light", event!!.values[0].toString() )
        var lightVal = event!!.values[0]
        if(lightVal > 10 && !isRunning){
            txtView_light.setBackgroundColor(getColor(R.color.colorAccent))
            txtView_light.text ="Light Turned On"
            isRunning = true
            try{
//                Toast.makeText(this, "Change detected", Toast.LENGTH_SHORT).show()
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setDataSource("https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_2MG.mp3")
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()

            }catch (ex: Exception){
                Log.e("MediaPlayer error", ex.message.toString())
            }

        }
        else if(lightVal < 10){
            txtView_light.setBackgroundColor(getColor(R.color.colorWhite))
            txtView_light.text ="Light Turned Off"
            if(mediaPlayer != null) mediaPlayer!!.stop()
            isRunning = false
        }
    }
}
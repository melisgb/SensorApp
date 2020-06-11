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
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_GotoShake.setOnClickListener {
            val intent = Intent(this, ShakeActivity::class.java)
            startActivity(intent)
        }
        btn_GotoLight.setOnClickListener {
            val intent = Intent(this, LightActivity::class.java)
            startActivity(intent)
        }
    }
}

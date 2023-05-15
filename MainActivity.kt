package com.example.task71

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.createAdvert).setOnClickListener {
            startActivity(Intent(this,Create_Advert::class.java))
        }

        findViewById<Button>(R.id.showItems).setOnClickListener {
            startActivity(Intent(this,Show_Advert::class.java))
        }
    }
}
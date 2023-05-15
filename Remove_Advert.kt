package com.example.task71

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class RemoveAdvert : AppCompatActivity() {

    private lateinit var textViewHeading: TextView
    private lateinit var textViewDetail: TextView
    private lateinit var databaseClass: db_Class
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_advert)

        // Initialize the Database_Class
        databaseClass = db_Class(this)

        textViewHeading = findViewById(R.id.heading)
        textViewDetail = findViewById(R.id.detail)

        val id = intent.getIntExtra("id",-1)

        // Check if a valid ID was received
        if (id != -1) {
            // Retrieve the Lost_Found from the database using the ID
            val lostAndFoundModel = databaseClass.getDataById(id)

            // Set the heading TextView with the "isLostOrFound" and "name" properties
            textViewHeading.text = "${lostAndFoundModel?.isLostOrFound}: ${lostAndFoundModel?.name}"

            // Set the detail with the formatted details of the LostAndFound
            textViewDetail.text = StringBuilder().apply {
                append(lostAndFoundModel?.date)
                append("\n")
                append(lostAndFoundModel?.location)
                append("\n")
                append(lostAndFoundModel?.phone)
                append("\n")
                append(lostAndFoundModel?.description)
            }.toString()
        }

        // Set a click for the remove button
        findViewById<Button>(R.id.remove).setOnClickListener {
            // Check if a valid ID was received from the previous activity
            if (id != -1) {
                // Delete the data from the database using the ID
                databaseClass.deleteDataById(id)
                finish()
            }
        }

    }
}
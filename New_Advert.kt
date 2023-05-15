package com.example.task71

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton

class Create_Advert : AppCompatActivity() {

    private lateinit var radioButtonLost: RadioButton
    private lateinit var radioButtonFound: RadioButton
    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var datePicker: ImageView
    private lateinit var database: db_Class
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_adver)

        // Initialize database
        database = db_Class(this)

        // Initialize views
        radioButtonLost = findViewById(R.id.lost)
        radioButtonFound = findViewById(R.id.found)
        nameEditText = findViewById(R.id.name)
        phoneEditText = findViewById(R.id.phone)
        descriptionEditText = findViewById(R.id.description)
        dateEditText = findViewById(R.id.date)
        locationEditText = findViewById(R.id.location)
        datePicker = findViewById(R.id.datePicker)

        // Set click for the date
        datePicker.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this)
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                dateEditText.setText("$dayOfMonth/$month/$year")
            }
        }

        // Set click for the save button
        findViewById<Button>(R.id.save).setOnClickListener {
            // Inserting data in database
            database.insertData(
                LostAndFound(
                    isLostOrFound = if (radioButtonLost.isChecked) "Lost" else "Found",
                    name = nameEditText.text.toString(),
                    phone = phoneEditText.text.toString(),
                    description = descriptionEditText.text.toString(),
                    date = dateEditText.text.toString(),
                    location = locationEditText.text.toString(),
                    id = 0
                )
            )
            finish()
        }
    }
}
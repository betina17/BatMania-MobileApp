// UpdateColonyActivity
package com.example.batmania

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UpdateColonyActivity : AppCompatActivity() {

    private var colonyId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_colony)

        val latitudeInput: EditText = findViewById(R.id.input_latitude)
        val longitudeInput: EditText = findViewById(R.id.input_longitude)
        val dateInput: EditText = findViewById(R.id.input_date)
        val timeInput: EditText = findViewById(R.id.input_time)
        val descriptionInput: EditText = findViewById(R.id.input_description)

        val updateButton: Button = findViewById(R.id.update_colony_button)

        // Retrieve the existing colony data passed from MainActivity
        colonyId = intent.getIntExtra("id", -1)
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        val date = intent.getStringExtra("date") ?: ""
        val time = intent.getStringExtra("time") ?: ""
        val description = intent.getStringExtra("description") ?: ""

        // Pre-populate the fields with existing data
        latitudeInput.setText(latitude.toString())
        longitudeInput.setText(longitude.toString())
        dateInput.setText(date)
        timeInput.setText(time)
        descriptionInput.setText(description)

        updateButton.setOnClickListener {
            val updatedLatitudeStr = latitudeInput.text.toString()
            val updatedLongitudeStr = longitudeInput.text.toString()
            val updatedDate = dateInput.text.toString()
            val updatedTime = timeInput.text.toString()
            val updatedDescription = descriptionInput.text.toString()

            // Validate input fields
            if (updatedLatitudeStr.isBlank() || updatedLongitudeStr.isBlank() || updatedDate.isBlank() || updatedTime.isBlank() || updatedDescription.isBlank()) {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedLatitude = updatedLatitudeStr.toDoubleOrNull()
            val updatedLongitude = updatedLongitudeStr.toDoubleOrNull()
            if (updatedLatitude == null || updatedLongitude == null) {
                Toast.makeText(this, "Latitude and Longitude must be valid numbers.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // After validation, send updated colony data back to MainActivity
            val resultIntent = Intent()
            resultIntent.putExtra("id", colonyId) // Pass colony ID
            resultIntent.putExtra("latitude", updatedLatitude)
            resultIntent.putExtra("longitude", updatedLongitude)
            resultIntent.putExtra("date", updatedDate)
            resultIntent.putExtra("time", updatedTime)
            resultIntent.putExtra("description", updatedDescription)

            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Close UpdateColonyActivity
        }
    }
}

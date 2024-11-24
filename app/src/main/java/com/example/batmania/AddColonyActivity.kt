package com.example.batmania

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddColonyActivity : AppCompatActivity() {

    private var colonyId: Int = -1  // Default to an invalid ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_colony)

        val latitudeInput: EditText = findViewById(R.id.input_latitude)
        val longitudeInput: EditText = findViewById(R.id.input_longitude)
        val dateInput: EditText = findViewById(R.id.input_date)
        val timeInput: EditText = findViewById(R.id.input_time)
        val descriptionInput: EditText = findViewById(R.id.input_description)

        val addButton: Button = findViewById(R.id.add_colony_button)

        // Get the intent data if available for editing
        colonyId = intent.getIntExtra("id", -1)
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        val date = intent.getStringExtra("date") ?: ""
        val time = intent.getStringExtra("time") ?: ""
        val description = intent.getStringExtra("description") ?: ""

        // If we are editing an existing colony, populate the fields with the data
        if (colonyId != -1) {
            latitudeInput.setText(latitude.toString())
            longitudeInput.setText(longitude.toString())
            dateInput.setText(date)
            timeInput.setText(time)
            descriptionInput.setText(description)
        }

        addButton.setOnClickListener {
            val latitudeStr = latitudeInput.text.toString()
            val longitudeStr = longitudeInput.text.toString()
            val date = dateInput.text.toString()
            val time = timeInput.text.toString()
            val description = descriptionInput.text.toString()

            // Validate input fields
            if (latitudeStr.isBlank() || longitudeStr.isBlank() || date.isBlank() || time.isBlank() || description.isBlank()) {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val latitude = latitudeStr.toDoubleOrNull()
            val longitude = longitudeStr.toDoubleOrNull()

            if (latitude == null || longitude == null) {
                Toast.makeText(this, "Latitude and Longitude must be valid numbers.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Send the result back to MainActivity with updated colony data
            val resultIntent = Intent()
            resultIntent.putExtra("id", colonyId)  // Pass the ID of the colony (even if it's new)
            resultIntent.putExtra("latitude", latitude)
            resultIntent.putExtra("longitude", longitude)
            resultIntent.putExtra("date", date)
            resultIntent.putExtra("time", time)
            resultIntent.putExtra("description", description)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}

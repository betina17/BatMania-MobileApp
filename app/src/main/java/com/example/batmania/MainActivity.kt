package com.example.batmania

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.batmania.adapters.ColonyAdapter
import com.example.batmania.models.Colony
import com.example.batmania.viewmodels.ColonyViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ColonyAdapter
    private val viewModel: ColonyViewModel by viewModels()

    // Register for activity result (Add colony)
    private val addColonyLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val latitude = result.data?.getDoubleExtra("latitude", 0.0) ?: return@registerForActivityResult
            val longitude = result.data?.getDoubleExtra("longitude", 0.0) ?: return@registerForActivityResult
            val date = result.data?.getStringExtra("date") ?: return@registerForActivityResult
            val time = result.data?.getStringExtra("time") ?: return@registerForActivityResult
            val description = result.data?.getStringExtra("description") ?: return@registerForActivityResult

            // Log the received data
            Log.d("MainActivity", "Received: Latitude: $latitude, Longitude: $longitude, Date: $date, Time: $time, Description: $description")

            // Add new colony to ViewModel
            viewModel.addColony(latitude, longitude, date, time, description)
        }
    }

    // Register for updating an existing colony
    private val updateColonyLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val id = result.data?.getIntExtra("id", -1) ?: return@registerForActivityResult
            val latitude = result.data?.getDoubleExtra("latitude", 0.0) ?: return@registerForActivityResult
            val longitude = result.data?.getDoubleExtra("longitude", 0.0) ?: return@registerForActivityResult
            val date = result.data?.getStringExtra("date") ?: return@registerForActivityResult
            val time = result.data?.getStringExtra("time") ?: return@registerForActivityResult
            val description = result.data?.getStringExtra("description") ?: return@registerForActivityResult

            // Update the colony in ViewModel
            val updatedColony = Colony(id, latitude, longitude, date, time, description)
            viewModel.updateColony(updatedColony)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe the colonies list from the ViewModel
        viewModel.colonies.observe(this, Observer { colonyList ->
            // Create the adapter and set the list of colonies
            adapter = ColonyAdapter(colonyList, object : ColonyAdapter.OnColonyClickListener {
                override fun onEditClick(colony: Colony) {
                    // When the colony item is clicked, launch the AddColonyActivity for editing
                    val intent = Intent(this@MainActivity, UpdateColonyActivity::class.java).apply {
                        putExtra("id", colony.id)
                        putExtra("latitude", colony.latitude)
                        putExtra("longitude", colony.longitude)
                        putExtra("date", colony.date)
                        putExtra("time", colony.time)
                        putExtra("description", colony.description)
                    }
                    updateColonyLauncher.launch(intent)  // Launch the edit activity
                }

                override fun onDeleteClick(colony: Colony) {
                    // Handle colony deletion if needed
                    val dialog = AlertDialog.Builder(this@MainActivity)
                        .setTitle("Delete Colony")
                        .setMessage("Are you sure you want to delete this colony?")
                        .setPositiveButton("Yes") { _, _ ->
                            // Delete the colony
                            viewModel.deleteColony(colony.id)
                            adapter.notifyDataSetChanged()  // Notify the adapter that data has changed
                        }
                        .setNegativeButton("No") { dialogInterface, _ ->
                            dialogInterface.dismiss()
                        }
                        .create()
                    dialog.show()
                }
            })

            recyclerView.adapter = adapter
        })

        // Set Add Colony button listener
        findViewById<Button>(R.id.Add_button).setOnClickListener {
            val intent = Intent(this, AddColonyActivity::class.java)
            addColonyLauncher.launch(intent)
        }
    }
}

package com.example.batmania.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.batmania.models.Colony

class ColonyViewModel : ViewModel() {

    // List of colonies stored in LiveData for UI observation
    val colonies = MutableLiveData<List<Colony>>(mutableListOf())

    // Counter for auto-generating unique IDs
    private var currentId = 0

    init {
        // Adding some hardcoded colonies when the ViewModel is created
        addHardcodedColonies()
    }

    // Function to add some hardcoded colonies
    private fun addHardcodedColonies() {
        val hardcodedColonies = listOf(
            Colony(currentId++, 10.123, 20.456, "2024-11-24", "12:00 PM", "Bat colony near the river"),
            Colony(currentId++, 12.345, 23.567, "2024-11-23", "3:30 PM", "Bat colony in the forest"),
            Colony(currentId++, 14.567, 25.789, "2024-11-22", "9:00 AM", "Bat colony in the cave"),
            Colony(currentId++, 16.789, 27.890, "2024-11-21", "6:15 PM", "Bat colony on the cliff")
        )

        // Set the hardcoded colonies to the LiveData
        colonies.value = hardcodedColonies
    }

    // Function to add a new colony
    fun addColony(latitude: Double, longitude: Double, date: String, time: String, description: String) {
        val newColony = Colony(currentId++, latitude, longitude, date, time, description)
        val updatedColonies = colonies.value?.toMutableList() ?: mutableListOf()
        updatedColonies.add(newColony)
        colonies.value = updatedColonies
    }

    // Function to update an existing colony
    fun updateColony(updatedColony: Colony) {
        // Ensure colonies.value is not null and then map over it to update the colony
        val updatedList = colonies.value?.map { colony ->
            if (colony.id == updatedColony.id) updatedColony else colony
        } ?: mutableListOf() // If colonies.value is null, use an empty list

        colonies.value = updatedList
    }

    // Function to delete a colony by its ID
    fun deleteColony(colonyId: Int) {
        // Filter out the colony with the matching ID
        val updatedList = colonies.value?.filterNot { it.id == colonyId } ?: mutableListOf()
        colonies.value = updatedList
    }
}

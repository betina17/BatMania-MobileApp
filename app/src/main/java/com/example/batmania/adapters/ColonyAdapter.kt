package com.example.batmania.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.batmania.R
import com.example.batmania.models.Colony

class ColonyAdapter(
    private val colonies: List<Colony>,
    private val listener: OnColonyClickListener
) : RecyclerView.Adapter<ColonyAdapter.ColonyViewHolder>() {

    interface OnColonyClickListener {
        fun onEditClick(colony: Colony)
        fun onDeleteClick(colony: Colony)
    }

    inner class ColonyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val latitude: TextView = view.findViewById(R.id.colony_latitude)
        val longitude: TextView = view.findViewById(R.id.colony_longitude)
        val date: TextView = view.findViewById(R.id.colony_date)
        val time: TextView = view.findViewById(R.id.colony_time)
        val description: TextView = view.findViewById(R.id.colony_description)
        val deleteButton: Button = view.findViewById(R.id.delete_button)

        init {
            // Set the entire item as clickable to trigger onEditClick
            itemView.setOnClickListener {
                val colony = colonies[adapterPosition]
                listener.onEditClick(colony)  // Trigger the edit click event
            }

            // Set click listener for delete button
            deleteButton.setOnClickListener {
                val colony = colonies[adapterPosition]
                listener.onDeleteClick(colony)  // Trigger the delete click event
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColonyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.colony_item_view, parent, false)  // Make sure the layout includes a delete button
        return ColonyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColonyViewHolder, position: Int) {
        val colony = colonies[position]
        holder.latitude.text = "Latitude: ${colony.latitude}"
        holder.longitude.text = "Longitude: ${colony.longitude}"
        holder.date.text = "Date: ${colony.date}"
        holder.time.text = "Time: ${colony.time}"
        holder.description.text = "Description: ${colony.description}"
    }

    override fun getItemCount(): Int = colonies.size
}

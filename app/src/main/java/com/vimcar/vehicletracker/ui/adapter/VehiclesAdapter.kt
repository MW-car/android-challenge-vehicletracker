package com.vimcar.vehicletracker.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vimcar.vehicletracker.R
import com.vimcar.vehicletracker.domain.model.Vehicle

class VehiclesAdapter :
    ListAdapter<Vehicle, VehiclesAdapter.VehicleViewHolder>(VehicleViewItemDiffCallback) {

    class VehicleViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(vehicle: Vehicle) {
            view.findViewById<TextView>(R.id.txt).text = vehicle.model
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_vehicle, parent, false)
        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object VehicleViewItemDiffCallback : DiffUtil.ItemCallback<Vehicle>() {

        override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
            return oldItem == newItem
        }

    }
}

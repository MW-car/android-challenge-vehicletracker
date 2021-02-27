package com.vimcar.vehicletracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vimcar.vehicletracker.R
import com.vimcar.vehicletracker.databinding.ListItemVehicleBinding
import com.vimcar.vehicletracker.domain.model.Vehicle

class VehiclesAdapter :
    ListAdapter<Vehicle, VehiclesAdapter.VehicleViewHolder>(VehicleViewItemDiffCallback) {

    class VehicleViewHolder(private val viewBinding: ListItemVehicleBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(vehicle: Vehicle) {
            viewBinding.apply {
                licensePlateTextView.text = vehicle.licensePlate
                carTextView.text = carTextView.context.getString(
                    R.string.car_brand_model,
                    vehicle.brand,
                    vehicle.model
                )
                nicknameTextView.text = if (vehicle.nickname.isNullOrEmpty()) {
                    ""
                } else {
                    vehicle.nickname
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        return VehicleViewHolder(
            ListItemVehicleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
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

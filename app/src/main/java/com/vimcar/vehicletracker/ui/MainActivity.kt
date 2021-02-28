package com.vimcar.vehicletracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vimcar.vehicletracker.R
import com.vimcar.vehicletracker.databinding.ActivityMainBinding
import com.vimcar.vehicletracker.domain.model.Vehicle

class MainActivity : AppCompatActivity(), VehicleOverviewFragment.OnVehicleClickListener {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }

    override fun onVehicleClick(vehicle: Vehicle) {
        viewBinding.fragmentContainerView.apply {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, MapFragment.newInstance(vehicle.id))
                .addToBackStack(null)
                .commit()
        }
    }
}

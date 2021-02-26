package com.vimcar.vehicletracker.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vimcar.vehicletracker.R
import com.vimcar.vehicletracker.di.component.DaggerVehicleOverViewComponent
import com.vimcar.vehicletracker.ui.adapter.VehiclesAdapter
import com.vimcar.vehicletracker.viewmodel.VehicleOverviewViewModel
import com.vimcar.vehicletracker.viewmodel.VehiclesViewState
import javax.inject.Inject

class VehicleOverviewFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel: VehicleOverviewViewModel by viewModels { viewModelProviderFactory }
    private val adapter = VehiclesAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerVehicleOverViewComponent.factory().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vehicle_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.vehiclesRecyclerView).adapter = adapter

        viewModel.vehicles.observe(this.viewLifecycleOwner) { viewState ->
            when (viewState) {
                is VehiclesViewState.Success -> {
                    adapter.submitList(viewState.vehicles)
                }
                is VehiclesViewState.Loading -> {
                }
                is Error -> {
                }
            }
        }

        viewModel.loadVehicles()
    }
}

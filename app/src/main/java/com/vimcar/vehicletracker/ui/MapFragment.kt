package com.vimcar.vehicletracker.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.vimcar.vehicletracker.R
import com.vimcar.vehicletracker.databinding.FragmentMapBinding
import com.vimcar.vehicletracker.di.component.DaggerVehiclesMapComponent
import com.vimcar.vehicletracker.domain.model.Vehicle
import com.vimcar.vehicletracker.domain.model.VehicleLastPosition
import com.vimcar.vehicletracker.viewmodel.VehiclesMapViewModel
import javax.inject.Inject

private const val KEY_VEHICLE_ID = "vehicle_id"
private const val ZOOM = 10.0f

class MapFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel: VehiclesMapViewModel by viewModels { viewModelProviderFactory }

    private var viewBinding: FragmentMapBinding? = null
    private val addedMarkers: MutableList<Marker> = mutableListOf()

    private var map: GoogleMap? = null
    private lateinit var pinIconDescriptor: BitmapDescriptor

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val selectedCarId = arguments?.getInt(KEY_VEHICLE_ID)
            ?: throw IllegalStateException("Selected car not available")
        DaggerVehiclesMapComponent.factory().create(selectedCarId).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMapBinding.inflate(LayoutInflater.from(context), container, false)
        viewBinding?.vehiclesMap?.onCreate(savedInstanceState)
        return viewBinding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeMarkerIcon()

        viewBinding?.apply {
            vehiclesMap.getMapAsync {
                map = it

                viewModel.loadVehicles()
                viewModel.vehicles.observe(viewLifecycleOwner) { vehicles ->
                    renderVehiclesMarkers(vehicles)
                }

                viewModel.selectedVehicleLocation.observe(viewLifecycleOwner) { selectedLocation ->
                    zoomToLocation(selectedLocation)
                }

                recenterButton.setOnClickListener { viewModel.onRecenterButtonClick() }
            }
        }
    }

    private fun renderVehiclesMarkers(vehicles: List<Vehicle>) {
        clearMarkers()
        map?.apply {
            vehicles
                .map { createMarker(it) }
                .forEach {
                    addedMarkers.add(addMarker(it))
                }
        }

    }

    private fun createMarker(vehicle: Vehicle): MarkerOptions {
        return MarkerOptions()
            .position(LatLng(vehicle.lastPosition.latitude, vehicle.lastPosition.longitude))
            .title(getString(R.string.car_brand_model, vehicle.brand, vehicle.model))
            .snippet(vehicle.licensePlate)
            .icon(pinIconDescriptor)
    }

    private fun clearMarkers() {
        addedMarkers.forEach { it.remove() }
        addedMarkers.clear()
    }

    private fun zoomToLocation(location: VehicleLastPosition) {
        val update = CameraUpdateFactory.newCameraPosition(
            CameraPosition.fromLatLngZoom(LatLng(location.latitude, location.longitude), ZOOM)
        )
        map?.animateCamera(update)
    }

    private fun initializeMarkerIcon() {
        val markerDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_location, null)?.mutate()
        markerDrawable?.setTint(ContextCompat.getColor(requireContext(), R.color.purple_700))
        val markerBitmap = markerDrawable?.toBitmap()
        pinIconDescriptor = BitmapDescriptorFactory.fromBitmap(markerBitmap)
    }

    override fun onResume() {
        super.onResume()
        viewBinding?.vehiclesMap?.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewBinding?.vehiclesMap?.onPause()
    }

    override fun onStop() {
        super.onStop()
        viewBinding?.vehiclesMap?.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewBinding?.vehiclesMap?.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding?.vehiclesMap?.onDestroy()
    }

    companion object {
        fun newInstance(vehicleId: Int): MapFragment {
            return MapFragment().apply {
                arguments = Bundle().apply { putInt(KEY_VEHICLE_ID, vehicleId) }
            }
        }
    }
}

package com.vimcar.vehicletracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vimcar.vehicletracker.domain.model.Vehicle
import com.vimcar.vehicletracker.domain.model.VehicleLastPosition
import com.vimcar.vehicletracker.domain.usecase.LoadVehiclesUseCase
import com.vimcar.vehicletracker.util.ReactiveTransformer
import com.vimcar.vehicletracker.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class VehiclesMapViewModel @Inject constructor(
    private val selectedCarId: Int,
    private val loadVehiclesUseCase: LoadVehiclesUseCase,
    private val reactiveTransformer: ReactiveTransformer
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var _vehicles = MutableLiveData<List<Vehicle>>()
    val vehicles: LiveData<List<Vehicle>> = _vehicles

    val selectedVehicleLocation = SingleLiveEvent<VehicleLastPosition>()
    private var selectedVehicleLastPosition: VehicleLastPosition? = null

    val error = SingleLiveEvent<VehiclesViewState>()


    fun loadVehicles() {
        if (vehicles.value == null) {
            compositeDisposable.add(
                loadVehiclesUseCase()
                    .compose(reactiveTransformer.ioSingleTransformer())
                    .subscribeBy(
                        onError = {
                            // TODO handle error state properly, Error here can be a separate type
                            error.postValue(VehiclesViewState.Error)
                        },
                        onSuccess = { vehicles ->
                            _vehicles.postValue(vehicles)

                            vehicles.filter { it.id == selectedCarId }.map { it.lastPosition }
                                .firstOrNull()?.let {
                                    selectedVehicleLastPosition = it
                                    selectedVehicleLocation.postValue(it)
                                }
                        }
                    )
            )
        }
    }

    fun onRecenterButtonClick() {
        selectedVehicleLastPosition?.let {
            selectedVehicleLocation.postValue(it)
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

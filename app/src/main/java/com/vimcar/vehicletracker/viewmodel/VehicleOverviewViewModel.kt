package com.vimcar.vehicletracker.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vimcar.vehicletracker.domain.usecase.LoadVehiclesUseCase
import com.vimcar.vehicletracker.util.ReactiveTransformer
import com.vimcar.vehicletracker.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class VehicleOverviewViewModel @Inject constructor(
    private val loadVehiclesUseCase: LoadVehiclesUseCase,
    private val reactiveTransformer: ReactiveTransformer
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var _vehicles = MutableLiveData<VehiclesViewState>()
    private var _error = SingleLiveEvent<VehiclesViewState>()

    val vehiclesOverViewViewState = MediatorLiveData<VehiclesViewState>().apply {
        addSource(_vehicles) { this.postValue(it) }
        addSource(_error) { this.postValue(it) }
    }

    fun loadVehicles() {
        if (vehiclesOverViewViewState.value == null) {
            refreshVehicles()
        }
    }

    fun refreshVehicles() {
        compositeDisposable.add(getLoadVehiclesDisposable())
    }

    private fun getLoadVehiclesDisposable(): Disposable {
        return loadVehiclesUseCase()
            .compose(reactiveTransformer.ioSingleTransformer())
            .doOnSubscribe {
                _vehicles.postValue(VehiclesViewState.Loading)
            }
            .subscribeBy(
                onError = {
                    _error.postValue(VehiclesViewState.Error)
                },
                onSuccess = {
                    _vehicles.postValue(VehiclesViewState.Success(it))
                }
            )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

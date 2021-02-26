package com.vimcar.vehicletracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vimcar.vehicletracker.domain.usecase.LoadVehiclesUseCase
import com.vimcar.vehicletracker.util.ReactiveTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class VehicleOverviewViewModel @Inject constructor(
    private val loadVehiclesUseCase: LoadVehiclesUseCase,
    private val reactiveTransformer: ReactiveTransformer
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var _vehicles = MutableLiveData<VehiclesViewState>()
    val vehicles: LiveData<VehiclesViewState> = _vehicles

    fun loadVehicles() {
        if (vehicles.value == null) {
            compositeDisposable.add(loadVehiclesUseCase()
                .compose(reactiveTransformer.ioSingleTransformer())
                .doOnSubscribe {
                    _vehicles.postValue(VehiclesViewState.Loading)
                }
                .subscribeBy(
                    onError = {
                        _vehicles.postValue( VehiclesViewState.Error)
                    },
                    onSuccess = {
                        _vehicles.postValue(VehiclesViewState.Success(it))
                    }
                )
            )
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

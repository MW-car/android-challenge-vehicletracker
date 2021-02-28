package com.vimcar.vehicletracker.di.module

import androidx.lifecycle.ViewModel
import com.vimcar.vehicletracker.di.viewmodel.ViewModelKey
import com.vimcar.vehicletracker.viewmodel.VehiclesMapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class VehiclesMapViewModelBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(VehiclesMapViewModel::class)
    abstract fun bindViewModel(viewModel: VehiclesMapViewModel): ViewModel
}

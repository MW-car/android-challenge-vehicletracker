package com.vimcar.vehicletracker.di.module

import androidx.lifecycle.ViewModel
import com.vimcar.vehicletracker.di.viewmodel.ViewModelKey
import com.vimcar.vehicletracker.viewmodel.VehicleOverviewViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class VehicleOverviewViewModelBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(VehicleOverviewViewModel::class)
    abstract fun bindViewModel(viewModel: VehicleOverviewViewModel): ViewModel
}

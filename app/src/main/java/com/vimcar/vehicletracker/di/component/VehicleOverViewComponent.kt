package com.vimcar.vehicletracker.di.component

import com.vimcar.vehicletracker.di.module.*
import com.vimcar.vehicletracker.ui.VehicleOverviewFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        RxModule::class,
        RepositoryModule::class,
        ViewModelsCreatorFactoryModule::class,
        VehicleOverviewViewModelBindingModule::class
    ]
)
interface VehicleOverViewComponent {

    fun inject(fragment: VehicleOverviewFragment)

    @Component.Factory
    interface Factory {
        fun create(): VehicleOverViewComponent
    }
}

package com.vimcar.vehicletracker.di.component

import com.vimcar.vehicletracker.di.module.*
import com.vimcar.vehicletracker.ui.MapFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ApiModule::class,
        RxModule::class,
        RepositoryModule::class,
        CacheModule::class,
        ViewModelsCreatorFactoryModule::class,
        VehiclesMapViewModelBindingModule::class
    ]
)
interface VehiclesMapComponent {

    fun inject(fragment: MapFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            selectedCarId: Int
        ): VehiclesMapComponent
    }
}

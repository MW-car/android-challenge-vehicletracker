package com.vimcar.vehicletracker.di.module

import androidx.lifecycle.ViewModelProvider
import com.vimcar.vehicletracker.di.viewmodel.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelsCreatorFactoryModule {

    @Binds
    abstract fun provideViewModelsCreatorFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}

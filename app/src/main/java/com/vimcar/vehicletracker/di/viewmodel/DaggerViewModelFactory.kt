package com.vimcar.vehicletracker.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
class DaggerViewModelFactory @Inject constructor(
    private val viewModelCreators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModelCreator = viewModelCreators[modelClass]
            ?: viewModelCreators.asIterable()
                .firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalStateException("Couldn't create view model of type ${modelClass}")
        return viewModelCreator.get() as T
    }
}

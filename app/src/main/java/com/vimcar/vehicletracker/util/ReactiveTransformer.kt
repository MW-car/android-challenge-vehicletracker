package com.vimcar.vehicletracker.util

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleTransformer

open class ReactiveTransformer(val io: Scheduler, val main: Scheduler) {

    fun <T> ioSingleTransformer(): SingleTransformer<T, T>? {
        return singleTransformer(io)
    }

    private fun <T> singleTransformer(scheduler: Scheduler): SingleTransformer<T, T>? {
        return SingleTransformer { single: Single<T> ->
            single.subscribeOn(scheduler).observeOn(main)
        }
    }
}

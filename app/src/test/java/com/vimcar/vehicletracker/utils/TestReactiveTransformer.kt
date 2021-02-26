package com.vimcar.vehicletracker.utils

import com.vimcar.vehicletracker.util.ReactiveTransformer
import io.reactivex.schedulers.Schedulers

class TestReactiveTransformer :
    ReactiveTransformer(io = Schedulers.trampoline(), main = Schedulers.trampoline())

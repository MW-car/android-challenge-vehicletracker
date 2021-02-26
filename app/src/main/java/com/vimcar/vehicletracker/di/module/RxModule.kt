package com.vimcar.vehicletracker.di.module

import com.vimcar.vehicletracker.util.ReactiveTransformer
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
object RxModule {

    @Singleton
    @Provides
    fun providesReactiveTransformer(): ReactiveTransformer =
        ReactiveTransformer(io = Schedulers.io(), main = AndroidSchedulers.mainThread())
}

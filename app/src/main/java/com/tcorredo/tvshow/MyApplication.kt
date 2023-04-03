package com.tcorredo.tvshow

import android.app.Application
import com.tcorredo.tvshow.di.dataModule
import com.tcorredo.tvshow.di.domainModule
import com.tcorredo.tvshow.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    dataModule,
                    domainModule,
                    viewModelModule
                )
            )
        }
    }
}
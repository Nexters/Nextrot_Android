package com.nextrot.troter

import android.app.Application
import com.nextrot.troter.data.TestRepository
import com.nextrot.troter.data.TestRepositoryImpl
import com.nextrot.troter.main.PageViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single<TestRepository> { TestRepositoryImpl() }
    factory { PageViewModel(get()) }
}

class TroterApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TroterApplication)
            modules(appModule)
        }
    }
}
package com.example.breakingbad.di

import androidx.work.*
import com.example.breakingbad.background.RefreshDataWorker
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

fun provideRepeatingWorkRequests(constraints: Constraints) : PeriodicWorkRequest {
    return PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
        .setConstraints(constraints)
        .build()
}

val backgroundModule = module {
    single {
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(false)
            .setRequiresDeviceIdle(true)
            .build()
    }
    single { provideRepeatingWorkRequests(get())}
    single { WorkManager.getInstance() }
}

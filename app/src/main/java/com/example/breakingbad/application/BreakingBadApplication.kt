package com.example.breakingbad.application

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.breakingbad.background.RefreshDataWorker
import com.example.breakingbad.di.breakingBadModules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.get

class BreakingBadApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    //Typical Koin setup
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BreakingBadApplication)
            modules(breakingBadModules)
        }

        setupWorkManager()
    }

    //Configuring Work Manager to run a network request to characters once a day
    private fun setupWorkManager(){
        applicationScope.launch {
            val periodicWorkRequest = get(PeriodicWorkRequest::class.java)
            val workManager = get(WorkManager::class.java)
            workManager.enqueueUniquePeriodicWork(
                RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
        }
    }

}

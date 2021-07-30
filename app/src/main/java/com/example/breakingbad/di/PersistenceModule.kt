package com.example.breakingbad.di

import android.app.Application
import androidx.room.Room
import com.example.breakingbad.persistence.BreakingBadDao
import com.example.breakingbad.persistence.CharacterDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

fun provideDatabase(application: Application): CharacterDatabase {
    return Room.databaseBuilder(application, CharacterDatabase::class.java, "breakingbad.persistence")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()
}


fun provideDao(database: CharacterDatabase): BreakingBadDao {
    return database.breakingBadDao
}

val persistenceModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

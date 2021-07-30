package com.example.breakingbad.di

import com.example.breakingbad.repositry.CharactersRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { CharactersRepository(get(), get()) }
}

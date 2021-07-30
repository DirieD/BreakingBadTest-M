package com.example.breakingbad.di

import com.example.breakingbad.network.BASE_URL
import com.example.breakingbad.network.BreakingBadNetworkService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideRetrofit(okHttpClient: OkHttpClient, coroutineCallAdapterFactory: CoroutineCallAdapterFactory, gsonConverterFactory: GsonConverterFactory): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(coroutineCallAdapterFactory)
        .build()
}

fun provideBreakingBadNetworkService(retrofit: Retrofit): BreakingBadNetworkService = retrofit.create(
    BreakingBadNetworkService::class.java)

val networkModule = module {
    single { CoroutineCallAdapterFactory() }
    single { OkHttpClient.Builder().build() }
    single { GsonConverterFactory.create() }
    single { provideRetrofit(get(), get(), get()) }
    single { provideBreakingBadNetworkService(get()) }
}

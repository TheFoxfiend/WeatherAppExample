package com.inaki.weatherappexample.rest

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object Retrofit {

    // this okhttp client allow us to set some headers and interceptors to the request and response
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
                // here the interceptor for logging is been added
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                // timeouts for reading from service
            .readTimeout(30, TimeUnit.SECONDS)
            // timeouts for writing from service
            .writeTimeout(30, TimeUnit.SECONDS)
            // timeouts for connection from service
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * This method will provide the Network API by retrofit
     *
     * we access to it doing Retrofit.getNetworkApi()
     */
    fun getNetworkApi(): NetworkApi = run {
        return@run Retrofit.Builder()
            .baseUrl(NetworkApi.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            ))
            .client(okHttpClient)
            .build()
            .create(NetworkApi::class.java)
    }
}
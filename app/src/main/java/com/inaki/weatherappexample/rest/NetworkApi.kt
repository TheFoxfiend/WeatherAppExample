package com.inaki.weatherappexample.rest

import com.inaki.weatherappexample.model.CityForecast
import io.reactivex.*
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    // HERE we are setting our path to be attach to the base URL
    // we are going to enter the queries for city and API key
    // those queries are 'q' for city and 'appid' for API key

    // https://api.openweathermap.org/data/2.5/forecast?q=atlanta&appid=65d00499677e59496ca2f318eb68c049
    @GET(WEATHER_FORECAST)
    // here we are doing query passing the exact name inside the parenthesis
    fun getForecast(
        @Query("q") cityName: String,
        // here we are assigning the API key because we don't want it to be changed
        @Query("appid") apiKey: String = API_KEY
    ): Single<CityForecast>


    // observable: emitting data constantly (onNext, onComplete, onError)
    // Single: This emits data only one time (onSuccess and onError)
    // Flowable: The same thing as the observable but it will handle the backpressure
    // Maybe: This will send you the type of data and also a completable action (onSuccess, onError, onComplete)
    // it is a combination between the Single and Completable
    // completable: Emits the complete action without any type of data (onComplete, onError)

    // backpressure: The situation where data is generated faster than it can be processed

    // cold observable: The data will be emitted within the observable instantiation
    // hot observable: Data is produced outside the observable,
    // so when you observe on it you already have the data that will be emitted


    companion object {
        // here we are going to set our API key
        private const val API_KEY = "65d00499677e59496ca2f318eb68c049"

        // define another constant for the weather forecast path
        private const val WEATHER_FORECAST = "data/2.5/forecast"

        // defining our base url
        const val BASE_URL = "https://api.openweathermap.org/"
    }
}
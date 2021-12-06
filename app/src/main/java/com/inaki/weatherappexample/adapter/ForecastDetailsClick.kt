package com.inaki.weatherappexample.adapter

import com.inaki.weatherappexample.model.Forecast

interface ForecastDetailsClick {
    fun moveToForecastDetails(cityName: String, forecast: Forecast)
}
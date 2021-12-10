package com.inaki.weatherappexample.adapter

import com.inaki.weatherappexample.model.Forecast
import com.inaki.weatherappexample.model.Weather

interface ForecastDetailsClick {
    fun moveToForecastDetails(cityName: String, forecast: Forecast)
}
package com.inaki.weatherappexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inaki.weatherappexample.R
import com.inaki.weatherappexample.model.City
import com.inaki.weatherappexample.model.CityForecast
import com.inaki.weatherappexample.model.Forecast
import java.util.*

class ForecastAdapter(
    private val forecastDetailsClick: ForecastDetailsClick,
    private var city: City? = null,
    private val forecastList: MutableList<Forecast> = mutableListOf()
) : RecyclerView.Adapter<ForecastViewHolder>() {

    fun updateForecast(cityForecast: CityForecast) {
        city = cityForecast.city
        forecastList.clear()
        forecastList.addAll(cityForecast.list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        // creating a variable to inflate the layout
        val forecastView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.forecast_items, parent, false)
        // I am returning the view holder class
        return ForecastViewHolder(forecastView)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        // creating a variable to hold reference to the forecast object
        val forecast = forecastList[position]

        // setting values to the view holder
        holder.weather.text = forecast.weather[0].main
        holder.forecastDate.text = forecast.dtTxt
        holder.temperature.text = forecast.main.temp.toString()

        // getting the city and displaying name and country
        holder.city.text = String.format(Locale.getDefault(), city?.name + ", " + city?.country)

        holder.itemView.setOnClickListener {
            forecastDetailsClick.moveToForecastDetails(city?.name ?: "", forecast)
        }
    }

    override fun getItemCount(): Int = forecastList.size
}

class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // here we are creating the variables to hold the views
    // we take those views from the 'itemView' using 'findViewById'
    val city: TextView = itemView.findViewById(R.id.city_forecast)
    val weather: TextView = itemView.findViewById(R.id.weather_forecast)
    val temperature: TextView = itemView.findViewById(R.id.forecast_temp)
    val forecastDate: TextView = itemView.findViewById(R.id.forecast_date)
}
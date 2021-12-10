package com.inaki.weatherappexample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inaki.weatherappexample.R
import com.inaki.weatherappexample.databinding.FragmentCityForecastBinding
import com.inaki.weatherappexample.databinding.FragmentForecastDetailsBinding

class ForecastDetails : Fragment() {

    private var cityName: String? = null
    private var temperature: String? = null
    private var minTemp: String? = null
    private var dateToday: String? = null
    private var maxTemp: String? = null
    private var weather: String? = null
    private var weatherDesc: String? = null
    private var tempFeel: String? = null

    private lateinit var binding: FragmentForecastDetailsBinding

    /**
     * onCreate method will help to get the arguments from the previous fragment
     * we can store them into a global variables to be used in the whole fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // todo add the missing arguments from previous fragment
        // here we are getting the arguments and set them to the variables
        arguments?.let {
            cityName = it.getString(CITY_NAME)
            temperature = it.getString(TEMP_FORECAST)
            minTemp = it.getString(MIN_TEMP)
            dateToday = it.getString(DATE_TODAY)
            maxTemp = it.getString(MAX_TEMP)
            weather = it.getString(WEATHER)
            weatherDesc = it.getString(WEATHER_DESC)
            tempFeel = it.getString(TEMP_FEEL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)

        // here we are setting our views with the respective value
        binding.cityName.text = cityName
        binding.forecastMinTemp.text = "Minimum temperature: "+ minTemp
        binding.forecastTemp.text = "Temperature: "+ temperature
        binding.forecastDate.text = "Date: "+ dateToday
        binding.forecastMaxTemp.text = "Maximum temperature: "+ maxTemp
        binding.weatherForecast.text = weather
        binding.weatherDescription.text = weatherDesc
        binding.forecastTempFeels.text = "How warm it feels: "+ tempFeel

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForecastDetails()
    }
}
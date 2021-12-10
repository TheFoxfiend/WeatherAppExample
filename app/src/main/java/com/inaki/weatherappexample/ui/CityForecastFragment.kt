package com.inaki.weatherappexample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inaki.weatherappexample.R
import com.inaki.weatherappexample.adapter.ForecastAdapter
import com.inaki.weatherappexample.adapter.ForecastDetailsClick
import com.inaki.weatherappexample.databinding.FragmentCityForecastBinding
import com.inaki.weatherappexample.model.CityForecast
import com.inaki.weatherappexample.model.Forecast
import com.inaki.weatherappexample.model.Weather
import com.inaki.weatherappexample.rest.Retrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.math.RoundingMode

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

const val DATE_TODAY = "DATE_TODAY"
const val TEMP_FORECAST = "TEMP_FORECAST"
const val MIN_TEMP = "MIN_TEMP"
const val MAX_TEMP = "MAX_TEMP"
const val WEATHER = "WEATHER"
const val WEATHER_DESC = "WEATHER_DESC"
const val TEMP_FEEL = "TEMP_FEEL"

class CityForecastFragment : Fragment(), ForecastDetailsClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // this variable is for view binding
    private lateinit var binding: FragmentCityForecastBinding

    // variable to get the city
    private var cityName: String? = null

    private lateinit var forecastAdapter: ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        // assigning the forecast adapter with the new instance of the adapter
        forecastAdapter = ForecastAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityForecastBinding.inflate(inflater, container, false)
        cityName = arguments?.getString(CITY_NAME)

        // here I am setting my recycler view
        binding.forecastRecycler.apply {
            // adding the layout manager
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            // setting the adapter
            adapter = forecastAdapter
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // HERE we are going to make the network call
        cityName?.let {
            Retrofit.getNetworkApi().getForecast(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { forecast -> handleSuccess(forecast) },
                    { throwable -> handleError(throwable) }
                )
        } ?: Toast.makeText(requireContext(), "Please enter a valid city", Toast.LENGTH_LONG).show()
    }

    private fun handleError(throwable: Throwable) {
        Toast.makeText(requireContext(), throwable.localizedMessage, Toast.LENGTH_LONG).show()
    }

    private fun handleSuccess(forecast: CityForecast) {
        forecastAdapter.updateForecast(forecast)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CityForecastFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    /**
     * This method will help to move to the next fragment with the information needed
     */
    override fun moveToForecastDetails(cityName: String, forecast: Forecast) {
        //here we are finding the nav controller and navigating to details fragment
        // here we can add arguments to pass the data
        // TODO put the missing arguments needed in the next fragment
        findNavController().navigate(
            R.id.ForecastDetailsFragment,
            bundleOf(
                // here you are passing the data you need in next screen
                // this is a Pair means key-value data
                // and then it can be consume in next fragment calling arguments
                Pair(CITY_NAME, cityName),
                Pair(DATE_TODAY, forecast.dtTxt),
                Pair(TEMP_FORECAST, BigDecimal((forecast.main.temp - 273.15)*9/5+32).setScale(2, RoundingMode.HALF_EVEN).toString()),
                Pair(MIN_TEMP, BigDecimal((forecast.main.tempMin - 273.15)*9/5+32).setScale(2, RoundingMode.HALF_EVEN).toString()),
                Pair(MAX_TEMP, BigDecimal((forecast.main.tempMax - 273.15)*9/5+32).setScale(2, RoundingMode.HALF_EVEN).toString()),
                Pair(WEATHER, forecast.weather[0].main),
                Pair(WEATHER_DESC, forecast.weather[0].description),
                Pair(TEMP_FEEL, BigDecimal((forecast.main.feelsLike - 273.15)*9/5+32).setScale(2, RoundingMode.HALF_EVEN).toString())
            //BigDecimal((forecast.main.feelsLike - 273.15)*9/5+32).setScale(2, RoundingMode.HALF_EVEN)
            )
        )
    }
}
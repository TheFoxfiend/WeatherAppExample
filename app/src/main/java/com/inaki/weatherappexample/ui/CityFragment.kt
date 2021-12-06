package com.inaki.weatherappexample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.inaki.weatherappexample.R
import com.inaki.weatherappexample.databinding.FragmentCityBinding

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val CITY_NAME = "CITY_NAME"
private const val ARG_PARAM2 = "param2"

class CityFragment : Fragment() {
    private var param2: String? = null

    private lateinit var binding: FragmentCityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.searchForecast.setOnClickListener {
            val cityName = binding.cityName.text.toString()


            findNavController().navigate(
                R.id.CityForecastFragment,
                bundleOf(
                    Pair(CITY_NAME, cityName)
                )
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
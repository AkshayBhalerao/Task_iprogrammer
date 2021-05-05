package com.bhaleraoakshay.task_iprogrammer.ui.model

import com.bhaleraoakshay.task_iprogrammer.common.RequestCompleteListener
import com.bhaleraoakshay.task_iprogrammer.ui.model.data_class.City
import com.bhaleraoakshay.task_iprogrammer.ui.model.data_class.WeatherInfoResponse

interface WeatherInfoShowModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfo(cityName: String, callback: RequestCompleteListener<WeatherInfoResponse>)
}
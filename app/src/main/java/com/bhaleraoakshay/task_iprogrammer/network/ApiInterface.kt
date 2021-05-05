package com.bhaleraoakshay.task_iprogrammer.network

import com.bhaleraoakshay.task_iprogrammer.ui.model.data_class.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun callApiForWeatherInfo(@Query("q") cityName: String): Call<WeatherInfoResponse>
}
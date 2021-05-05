package com.bhaleraoakshay.task_iprogrammer.ui.model

import android.R.attr.data
import android.content.Context
import android.content.SharedPreferences
import com.bhaleraoakshay.task_iprogrammer.common.RequestCompleteListener
import com.bhaleraoakshay.task_iprogrammer.network.ApiInterface
import com.bhaleraoakshay.task_iprogrammer.network.RetrofitClient
import com.bhaleraoakshay.task_iprogrammer.ui.model.data_class.City
import com.bhaleraoakshay.task_iprogrammer.ui.model.data_class.WeatherInfoResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type


class WeatherInfoShowModelImpl(private val context: Context): WeatherInfoShowModel {

  /*  override fun getCityList(callback: RequestCompleteListener<MutableList<City>>) {
        try {

            val cityList: MutableList<City> = loadSharedPreferencesLogList(context)
            callback.onRequestSuccess(cityList)

        } catch (e: IOException) {
            e.printStackTrace()
            callback.onRequestFailed(e.localizedMessage!!)
        }
    }*/


     override fun getWeatherInfo(
         cityName: String,
         callback: RequestCompleteListener<WeatherInfoResponse>
     ) {

        val apiInterface: ApiInterface = RetrofitClient.client.create(ApiInterface::class.java)
        val call: Call<WeatherInfoResponse> = apiInterface.callApiForWeatherInfo(cityName)

        call.enqueue(object : Callback<WeatherInfoResponse> {

            override fun onResponse(
                call: Call<WeatherInfoResponse>,
                response: Response<WeatherInfoResponse>
            ) {
                if (response.body() != null)
                    callback.onRequestSuccess(response.body()!!)
                else
                    callback.onRequestFailed(response.message())
            }

            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!)
            }
        })
    }



  /*  private fun loadSharedPreferencesLogList(context: Context): MutableList<City> {
        var savedCity: List<City?> = ArrayList()
        val mPrefs = context.getSharedPreferences("CityList", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("myJson", "")
        savedCity = if (json!!.isEmpty()) {
            ArrayList()
        } else {
            val type: Type = object : TypeToken<List<City?>?>() {}.type
            gson.fromJson(json, type)
        }
        return savedCity as MutableList<City>
    }*/
 }
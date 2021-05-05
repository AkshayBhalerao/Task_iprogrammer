package com.bhaleraoakshay.task_iprogrammer.ui.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bhaleraoakshay.task_iprogrammer.common.RequestCompleteListener
import com.bhaleraoakshay.task_iprogrammer.ui.model.WeatherInfoShowModel
import com.bhaleraoakshay.task_iprogrammer.ui.model.data_class.City
import com.bhaleraoakshay.task_iprogrammer.ui.model.data_class.WeatherData
import com.bhaleraoakshay.task_iprogrammer.ui.model.data_class.WeatherInfoResponse
import com.bhaleraoakshay.task_iprogrammer.utils.kelvinToCelsius
import com.bhaleraoakshay.task_iprogrammer.utils.unixTimestampToDateTimeString
import com.bhaleraoakshay.task_iprogrammer.utils.unixTimestampToTimeString



class WeatherInfoViewModel : ViewModel() {


    val cityListLiveData = MutableLiveData<MutableList<City>>()
    val cityListFailureLiveData = MutableLiveData<String>()
    val weatherInfoLiveData = MutableLiveData<WeatherData>()
    val weatherInfoFailureLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()

    fun getWeatherInfo(cityName: String, model: WeatherInfoShowModel) {

        progressBarLiveData.postValue(true)

        model.getWeatherInfo(cityName, object :
            RequestCompleteListener<WeatherInfoResponse> {
            override fun onRequestSuccess(data: WeatherInfoResponse) {

                val weatherData = WeatherData(
                    dateTime = data.dt.unixTimestampToDateTimeString(),
                    temperature = data.main.temp.kelvinToCelsius().toString(),
                    cityAndCountry = "${data.name}, ${data.sys.country}",
                    weatherConditionIconUrl = "http://openweathermap.org/img/w/${data.weather[0].icon}.png",
                    sunrise = data.sys.sunrise.unixTimestampToTimeString(),
                    sunset = data.sys.sunset.unixTimestampToTimeString()
                )

                progressBarLiveData.postValue(false)

                weatherInfoLiveData.postValue(weatherData)
            }

            override fun onRequestFailed(errorMessage: String) {
                progressBarLiveData.postValue(false)
                weatherInfoFailureLiveData.postValue(errorMessage)
            }
        })
    }


}
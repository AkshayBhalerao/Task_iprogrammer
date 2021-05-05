package com.bhaleraoakshay.task_iprogrammer.ui.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bhaleraoakshay.task_iprogrammer.R
import com.bhaleraoakshay.task_iprogrammer.databinding.ActivityMainBinding
import com.bhaleraoakshay.task_iprogrammer.ui.model.WeatherInfoShowModel
import com.bhaleraoakshay.task_iprogrammer.ui.model.WeatherInfoShowModelImpl
import com.bhaleraoakshay.task_iprogrammer.ui.model.data_class.City
import com.bhaleraoakshay.task_iprogrammer.ui.model.data_class.WeatherData
import com.bhaleraoakshay.task_iprogrammer.ui.viewmodel.WeatherInfoViewModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.util.*


class MainActivity : AppCompatActivity() {


    private lateinit var model: WeatherInfoShowModel
    private lateinit var viewModel: WeatherInfoViewModel
    private lateinit var binding: ActivityMainBinding

    private var cityList: MutableList<City> = mutableListOf()

    var cities = ArrayList<String>()
    private lateinit var autotextView: AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model = WeatherInfoShowModelImpl(applicationContext)
        viewModel = ViewModelProviders.of(this).get(WeatherInfoViewModel::class.java)
        autotextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView_serachPlace)

        setLiveDataListeners()
        setViewClickListener()


        viewModel.getCityList(model)
    }

    private fun setViewClickListener() {
        autotextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged", s.toString())
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d("onTextChanged", s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                Log.d("afterTextChanged", s.toString())
            }
        })

        findViewById<Button>(R.id.button_actionSearch)?.setOnClickListener {
            val enteredText = "Selected " + autotextView.text.toString()

            if(cities.contains(enteredText)){
                Log.d("TAG", "item already present in list")
            }
            else{
                cities.add(enteredText)
                saveSharedPreferencesLogList(this, cities)
            }

            viewModel.getWeatherInfo(enteredText, model) // fetch weather info

            Toast.makeText(this, enteredText, Toast.LENGTH_SHORT).show()
        }

    }


    private fun setLiveDataListeners() {
        viewModel.cityListLiveData.observe(this,
            { cities -> setCityListData(cities) })

        viewModel.cityListFailureLiveData.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        })

        viewModel.progressBarLiveData.observe(this, Observer { isShowLoader ->
            if (isShowLoader)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        })

        viewModel.weatherInfoLiveData.observe(this, Observer { weatherData ->
            setWeatherInfo(weatherData)
        })


        viewModel.weatherInfoFailureLiveData.observe(this, Observer { errorMessage ->
            binding.outputGroup.visibility = View.GONE
            binding.tvErrorMessage.visibility = View.VISIBLE
            binding.tvErrorMessage.text = errorMessage
        })
    }

    private fun setCityListData(cityList: MutableList<City>) {
        this.cityList = cityList
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, this.cityList)
        autotextView.setAdapter(arrayAdapter)

    }

    private fun setWeatherInfo(weatherData: WeatherData) {

        binding.outputGroup.visibility = View.VISIBLE
        binding.tvErrorMessage.visibility = View.GONE

        binding.tvDateTime.text = weatherData.dateTime
        binding.tvTemperature.text = weatherData.temperature
        binding.tvCityCountry.text = weatherData.cityAndCountry
        Glide.with(this).load(weatherData.weatherConditionIconUrl).into(binding.ivWeatherCondition)
        binding.tvWeatherCondition.text = weatherData.weatherConditionIconDescription

    }

    private fun saveSharedPreferencesLogList(context: Context, cityList: ArrayList<String>) {
        val mPrefs: SharedPreferences =
            context.getSharedPreferences("CityList", Context.MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(cityList)
        prefsEditor.putString("myJson", json)
        prefsEditor.apply()
    }
}
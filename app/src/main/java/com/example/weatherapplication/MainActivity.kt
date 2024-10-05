package com.example.weatherapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private lateinit var repo: Repo
    private lateinit var weatherViewModelFactory: WeatherViewModelFactory
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var loader: ProgressBar

    private lateinit var edtCityName: EditText
    private lateinit var btnGetWeather: Button
    private lateinit var imgWeather: ImageView
    private lateinit var textWeather: TextView
    private lateinit var textCityStateName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        btnGetWeather.setOnClickListener {
            weatherViewModel.getWeatherDetail(edtCityName.text.toString())
        }

        weatherViewModel.weatherDetailLiveData.observe(this) {
            val currentWeatherType = it.current.condition.text
            val currentTempInC = it.current.temp_c

            textWeather.text = "$currentWeatherType, $currentTempInC"

            val imageLink = "https:${it.current.condition.icon}"
            Glide.with(this)
                .load(imageLink)
                .into(imgWeather)

            val cityName = it.location.name
            val state = it.location.region

            val cityAndState = "$cityName, $state"
            textCityStateName.text = cityAndState
        }

        weatherViewModel.isLoading.observe(this) {
            if (it) {
                loader.visibility = View.VISIBLE
            } else {
                loader.visibility = View.GONE
            }
        }

    }

    private fun init() {
        repo = Repo(RetrofitBuilder.getInstance())
        weatherViewModelFactory = WeatherViewModelFactory(repo)
        weatherViewModel = ViewModelProvider(this, weatherViewModelFactory).get(WeatherViewModel::class.java)

        loader = findViewById(R.id.loader)
        edtCityName = findViewById(R.id.edt_city_name)
        btnGetWeather = findViewById(R.id.btn_get_weather)
        imgWeather = findViewById(R.id.img_weather)
        textWeather = findViewById(R.id.text_weather)
        textCityStateName = findViewById(R.id.text_city_state)
    }

}
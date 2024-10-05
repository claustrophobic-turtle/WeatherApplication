package com.example.weatherapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.datamodel.WeatherResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repo: Repo
) : ViewModel() {

    val weatherDetailLiveData = MutableLiveData<WeatherResponseModel>()
    val isLoading = MutableLiveData<Boolean>(false)

    fun getWeatherDetail(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            val response = repo.getWeatherDetail(city)
            if(response.isSuccessful) {
                weatherDetailLiveData.postValue(response.body())
                isLoading.postValue(false)
            }
        }
    }

}
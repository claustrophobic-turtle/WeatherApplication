package com.example.weatherapplication

import com.example.weatherapplication.datamodel.WeatherResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {

    @GET("/v1/current.json?key=2b5dc9c8dccc4968a6655319242309")
    suspend fun getWeatherDetail(
        @Query("q") city: String
    ) : Response<WeatherResponseModel>

}
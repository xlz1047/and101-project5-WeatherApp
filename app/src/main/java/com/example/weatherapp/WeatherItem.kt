package com.example.weatherapp

data class WeatherItem(
    val date: String,
    val maxTemperature: Double,
    val minTemperature: Double,
    val weatherCode: Int
)
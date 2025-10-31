package com.example.weatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var tvTemperature: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvLocation: TextView
    private lateinit var ivWeatherIcon: ImageView
    private lateinit var btnRefresh: Button
    private lateinit var etCitySearch: EditText
    private lateinit var btnSearch: Button

    private val client = AsyncHttpClient()
    private var latitude = "39.9526"  // Default: Philadelphia latitude
    private var longitude = "-75.1652" // Default: Philadelphia longitude
    private var cityName = "Philadelphia, PA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI references
        tvTemperature = findViewById(R.id.tvTemperature)
        tvCondition = findViewById(R.id.tvCondition)
        tvLocation = findViewById(R.id.tvLocation)
        ivWeatherIcon = findViewById(R.id.ivWeatherIcon)
        btnRefresh = findViewById(R.id.btnRefresh)
        etCitySearch = findViewById(R.id.etCitySearch)
        btnSearch = findViewById(R.id.btnSearch)

        // Fetch weather initially
        fetchWeather()

        // Refresh weather on button click
        btnRefresh.setOnClickListener {
            fetchWeather()
        }

        // Search for city weather
        btnSearch.setOnClickListener {
            val searchCity = etCitySearch.text.toString().trim()
            if (searchCity.isNotEmpty()) {
                searchCityCoordinates(searchCity)
            } else {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchCityCoordinates(city: String) {
        // Using Open-Meteo's Geocoding API
        val url = "https://geocoding-api.open-meteo.com/v1/search?name=$city&count=1&language=en&format=json"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject) {
                if (response.has("results")) {
                    val results = response.getJSONArray("results")
                    if (results.length() > 0) {
                        val location = results.getJSONObject(0)
                        latitude = location.getDouble("latitude").toString()
                        longitude = location.getDouble("longitude").toString()
                        cityName = location.getString("name")

                        // Add country if available
                        if (location.has("country")) {
                            cityName += ", ${location.getString("country")}"
                        }

                        fetchWeather()
                    } else {
                        Toast.makeText(this@MainActivity, "City not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "City not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, throwable: Throwable?, errorResponse: JSONObject?) {
                Toast.makeText(this@MainActivity, "Failed to search city", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchWeather() {
        val url = "https://api.open-meteo.com/v1/forecast?latitude=$latitude&longitude=$longitude&current_weather=true"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject) {
                val current = response.getJSONObject("current_weather")
                val temperature = current.getDouble("temperature")
                val windspeed = current.getDouble("windspeed")
                val weatherCode = current.getInt("weathercode")

                tvTemperature.text = "Temperature: ${temperature}°C"
                tvCondition.text = "Wind Speed: $windspeed km/h"
                tvLocation.text = cityName

                // Simple weather icon mapping
                val iconUrl = when (weatherCode) {
                    0 -> "https://img.icons8.com/fluency/48/000000/sun.png"
                    in 1..3 -> "https://img.icons8.com/fluency/48/000000/partly-cloudy-day.png"
                    else -> "https://img.icons8.com/fluency/48/000000/cloud.png"
                }
                Glide.with(this@MainActivity).load(iconUrl).into(ivWeatherIcon)
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, throwable: Throwable?, errorResponse: JSONObject?) {
                tvTemperature.text = "Failed to load weather"
                tvCondition.text = ""
                tvLocation.text = ""
            }
        })
    }
}
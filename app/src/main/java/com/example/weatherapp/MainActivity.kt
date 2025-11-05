package com.example.weatherapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var tvLocation: TextView
    private lateinit var btnRefresh: Button
    private lateinit var etCitySearch: EditText
    private lateinit var btnSearch: Button
    private lateinit var rvWeatherForecast: RecyclerView

    private val weatherList = ArrayList<WeatherItem>()
    private lateinit var weatherAdapter: WeatherAdapter

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
        tvLocation = findViewById(R.id.tvLocation)
        btnRefresh = findViewById(R.id.btnRefresh)
        etCitySearch = findViewById(R.id.etCitySearch)
        btnSearch = findViewById(R.id.btnSearch)
        rvWeatherForecast = findViewById(R.id.rvWeatherForecast)

        // Setup RecyclerView
        rvWeatherForecast.layoutManager = LinearLayoutManager(this)
        weatherAdapter = WeatherAdapter(weatherList)
        rvWeatherForecast.adapter = weatherAdapter

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
        // Request 7-day daily forecast with max/min temperature and weather code
        val url = "https://api.open-meteo.com/v1/forecast?latitude=$latitude&longitude=$longitude&daily=temperature_2m_max,temperature_2m_min,weathercode&timezone=auto"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject) {
                try {
                    val daily = response.getJSONObject("daily")
                    val dates = daily.getJSONArray("time")
                    val maxTemps = daily.getJSONArray("temperature_2m_max")
                    val minTemps = daily.getJSONArray("temperature_2m_min")
                    val weatherCodes = daily.getJSONArray("weathercode")

                    // Clear previous data
                    weatherList.clear()

                    // Populate list with forecast data
                    for (i in 0 until dates.length()) {
                        val weatherItem = WeatherItem(
                            date = dates.getString(i),
                            maxTemperature = maxTemps.getDouble(i),
                            minTemperature = minTemps.getDouble(i),
                            weatherCode = weatherCodes.getInt(i)
                        )
                        weatherList.add(weatherItem)
                    }

                    // Update UI
                    tvLocation.text = cityName
                    weatherAdapter.notifyDataSetChanged()

                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Error parsing weather data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, throwable: Throwable?, errorResponse: JSONObject?) {
                Toast.makeText(this@MainActivity, "Failed to load weather", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
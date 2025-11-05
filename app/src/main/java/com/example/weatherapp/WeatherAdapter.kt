package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class WeatherAdapter(private val weatherList: ArrayList<WeatherItem>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvMaxTemp: TextView = itemView.findViewById(R.id.tvMaxTemp)
        val tvMinTemp: TextView = itemView.findViewById(R.id.tvMinTemp)
        val tvCondition: TextView = itemView.findViewById(R.id.tvCondition)
        val ivWeatherIcon: ImageView = itemView.findViewById(R.id.ivWeatherIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = weatherList[position]

        // Format date
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
        try {
            val date = inputFormat.parse(item.date)
            holder.tvDate.text = if (date != null) outputFormat.format(date) else item.date
        } catch (e: Exception) {
            holder.tvDate.text = item.date
        }

        // Display temperatures
        holder.tvMaxTemp.text = "High: ${item.maxTemperature}°C"
        holder.tvMinTemp.text = "Low: ${item.minTemperature}°C"

        // Map weather code to condition text and icon
        val (condition, iconUrl) = getWeatherInfo(item.weatherCode)
        holder.tvCondition.text = condition

        // Load icon using Glide
        Glide.with(holder.itemView.context)
            .load(iconUrl)
            .into(holder.ivWeatherIcon)
    }

    override fun getItemCount(): Int = weatherList.size

    private fun getWeatherInfo(code: Int): Pair<String, String> {
        return when (code) {
            0 -> Pair("Clear Sky", "https://img.icons8.com/fluency/48/000000/sun.png")
            in 1..3 -> Pair("Partly Cloudy", "https://img.icons8.com/fluency/48/000000/partly-cloudy-day.png")
            45, 48 -> Pair("Foggy", "https://img.icons8.com/fluency/48/000000/fog-day.png")
            in 51..67 -> Pair("Rainy", "https://img.icons8.com/fluency/48/000000/rain.png")
            in 71..77 -> Pair("Snow", "https://img.icons8.com/fluency/48/000000/snow.png")
            in 80..82 -> Pair("Rain Showers", "https://img.icons8.com/fluency/48/000000/rain.png")
            in 85..86 -> Pair("Snow Showers", "https://img.icons8.com/fluency/48/000000/snow.png")
            95 -> Pair("Thunderstorm", "https://img.icons8.com/fluency/48/000000/storm.png")
            else -> Pair("Cloudy", "https://img.icons8.com/fluency/48/000000/cloud.png")
        }
    }
}
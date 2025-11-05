# AND101 Project 6 - CYOAPI Part 2: RecyclerView Edition

Submitted by: **Xin Zheng**

Time spent: **8** hours spent in total

## Summary

**Weather Forecast App** is an android app that **displays a 7-day weather forecast for any city in the world using the Open-Meteo API. The app uses a RecyclerView to show daily forecasts with high/low temperatures, weather conditions, and icons.**

If I had to describe this project in three (3) emojis, they would be: **🌤️ 📜 🔄**

## Application Features

The following REQUIRED features are completed:

- [x] Make an API call to an API of your choice using AsyncHTTPClient
- [x] Implement a RecyclerView to display a list of entries from the API
- [x] Display at least three (3) pieces of data for each RecyclerView item

The following STRETCH features are implemented:

- [x] Add a UI element for the user to interact with API further
- [ ] Show a `Toast` or `Snackbar` when an item is clicked
- [ ] Add item dividers with `DividerItemDecoration`

The following EXTRA features are implemented:

- [x] Implemented CardView design for each forecast item
- [x] Added city search functionality to check weather worldwide
- [x] Used Glide library for efficient weather icon loading
- [x] Formatted dates to display as "Day, Month Date" format
- [x] Comprehensive weather code mapping (clear, cloudy, rainy, snowy, thunderstorm, etc.)
- [x] Added Toast messages for error handling
- [x] Integrated Geocoding API to convert city names to coordinates

## API Choice

My chosen API for this project is **Open-Meteo Weather API** (https://open-meteo.com/).

I also used the **Open-Meteo Geocoding API** (https://geocoding-api.open-meteo.com/) to convert city names to geographic coordinates.

## Video Demo

Here's a video / GIF that demos all of the app's implemented features:

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Demo' width='' alt='Video Demo' />

GIF created with **ScreenToGif**

## Notes

### What I Learned:
* How to implement RecyclerView with custom adapters in Android
* Creating and using data classes (models) for structured data
* Working with CardView for better UI design
* Implementing ViewHolder pattern for efficient RecyclerView performance
* Parsing complex JSON responses with arrays of daily forecast data
* Using SimpleDateFormat to format dates for better user experience
* The importance of notifyDataSetChanged() for updating RecyclerView data
* How to structure layouts using ConstraintLayout within RecyclerView items

### Challenges:
* Initially had type mismatch errors between List and ArrayList when setting up the adapter
* Understanding the difference between current weather API and daily forecast API endpoints
* Figuring out how to properly update RecyclerView data after API calls
* Learning to map weather codes to appropriate icons and descriptions
* Handling the layout constraints for RecyclerView items to display properly

### Improvements from Project 5:
* Upgraded from single weather display to 7-day forecast list
* Better organized code with separate adapter and data model files
* More comprehensive weather information with high/low temperatures
* Improved visual design with CardView and better spacing
* Enhanced user experience with formatted dates

## License

Copyright **2025** **Xin Zheng**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

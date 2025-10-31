# AND101 Project 5 - Choose Your Own API

Submitted by: **Xin Zheng**

Time spent: **5** hours spent in total

## Summary

**Weather App** is an android app that **displays current weather information for any city in the world using the Open-Meteo API. Users can search for cities and view temperature, wind speed, and weather conditions with icons.**

If I had to describe this project in three (3) emojis, they would be: **🌤️ 🌍 🔍**

## Application Features

The following REQUIRED features are completed:

- [x] Make an API call to an API of your choice using AsyncHTTPClient
- [x] Display at least three (3) pieces of data for each API entry retrieved
- [x] A working Button requests a new API entry and updates the data displayed

The following STRETCH features are implemented:

- [x] Add a query to the API request
  - The query I added is **city name (using Open-Meteo Geocoding API to convert city names to latitude/longitude coordinates)**
- [x] Build a UI to allow users to add that query

The following EXTRA features are implemented:

- [x] Added weather icons that change based on weather conditions
- [x] Implemented city search functionality to check weather worldwide
- [x] Used Glide library for efficient image loading
- [x] Added Toast messages for user feedback on search errors
- [x] Default location set to Philadelphia, PA

## API Choice

My chosen API for this project is **Open-Meteo Weather API** (https://open-meteo.com/).

I also used the **Open-Meteo Geocoding API** (https://geocoding-api.open-meteo.com/) to convert city names to geographic coordinates.

## Video Demo

Here's a video / GIF that demos all of the app's implemented features:

<img src='https://github.com/xlz1047/and101-project5-WeatherApp/blob/master/Result.gif' title='Video Demo' width='' alt='Video Demo' />

GIF created with **ScreenToGif** (or your chosen tool)

<!-- Recommended tools:
- [Kap](https://getkap.co/) for macOS
- [ScreenToGif](https://www.screentogif.com/) for Windows
- [peek](https://github.com/phw/peek) for Linux. -->

## Notes

### What I Learned:
- How to make HTTP requests using AsyncHTTPClient in Android
- Working with JSON responses and parsing weather data
- Implementing the Glide library for loading images from URLs
- Using multiple APIs together (Weather API + Geocoding API)
- Handling user input validation and error cases
- The importance of adding Internet permissions in AndroidManifest.xml

### Challenges:
- Initially forgot to add Internet permission, which caused "Failed to load weather" errors
- Had to figure out how to convert city names to coordinates using a geocoding API
- Learning how to properly parse JSON responses with nested objects

### Future Improvements:
- Add a 7-day forecast feature
- Display more weather details (humidity, precipitation, UV index)
- Save favorite cities
- Add background images that change based on weather conditions
- Implement current location detection using GPS

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

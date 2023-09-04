# Weather Notification App

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Stopping Notifications](#stopping-notifications)

## Introduction

The Weather Notification App is an Android application built in Java that provides users with weather forecasts for the next day, with a focus on notifying users about rain. It uses the OpenWeatherMap API to fetch weather data and sends notifications to users regarding rain forecasts. The app runs in the background even after closing it, ensuring that users stay informed about weather conditions.

## Features

- Fetches weather forecasts for the next day using the OpenWeatherMap API.
- Notifies users a day before if rain is forecasted.
- Rechecks and sends notifications every 12 hours if rain is still forecasted.
- Switches to a 3-hour checking interval if no rain is forecasted.
- Allows users to stop notifications at any time.

## Getting Started

### Prerequisites

Before you begin, make sure you have the following:

- Android Studio installed on your development machine.
- An OpenWeatherMap API key. You can obtain one by signing up at [OpenWeatherMap](https://openweathermap.org/).

### Installation

1. Clone the repository to your local machine:

   ```bash
        git clone https://github.com/your-username/weather-notification-app.git
   
2. Open the project in Android Studio.

3. In the project, locate the `gradle.properties` file and add your OpenWeatherMap API key:
   ```bash
   myApiKey="YOURAPIKEY"
3. Build and run the app on an Android emulator or a physical Android device.

## Usage

- **Launch the Weather Notification App on your Android device.**
  
- The app will fetch weather data for the next day and notify you if rain is forecasted.

- If rain is forecasted, you will receive a notification a day before.

- The app will recheck the forecast every 12 hours and send notifications accordingly.

- If no rain is forecasted, the app will switch to a 3-hour checking interval.

## Stopping Notifications

- If you no longer wish to receive weather notifications, you can stop them by following these steps:
  
  - Swipe down on the notification bar to view the Weather Notification App notification.

  - Tap the "Stop Notifications" button in the notification.

  - Notifications from the app will be disabled.

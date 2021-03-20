# Scrooge McDucks App

---

### Table of Contents

- [Description](#description)
- [How To Use](#how-to-use)
- [Class diagram](#class-diagram)

---

## Description

Android application which analyzes stock data. User can import stock data in a CSV file and find answers to following questions.
- How many days was the longest bullish (upward) trend within a given date range?
- Which dates within a given date range had a) the highest trading volume and b) the most 
significant stock price change within a day?
- Within a given date range, which dates had the best opening price compared to 5 days 
simple moving average (SMA 5)?

 
#### Technologies

- Android studio
- Java
- Hilt
- Android Jetpack LiveData and ViewModels

---

## How To Use

#### Installation

This project has been created using Android studio. To install the project into phone clone the project and open
the project inside Android studio. This repository also contains .apk (Android package file) which can be downloaded into phone 
and used for opening the application directly to Android phone. Minimun Android version is Android 5.0.

#### Features

When the app is lauched for the first time GameStop sample data is imported into the app. This data can be used for testing.

![Alt text](/screenshots/start.png "Starting screen" | width=100)

Add CSV files which contains stock data in a following way
“Date, Close/Last, Volume, Open, High, Low
01/19/2021, $127.83, 90757330, $127.78, $128.71, $126.938”
Sample data can be found from https://www.nasdaq.com/market-activity/stocks/aapl/historical.

Select a file
![Alt text](/screenshots/selectfile.png "Select file" | width=100)

Name the data. 
![Alt text](/screenshots/namefile.png "Name file" | width=100)

Analyze stock data by selecting an item from the list.
![Alt text](/screenshots/filedownloaded.png "File downloaded" | width=100)

Select a category and date range to analyze data.
![Alt text](/screenshots/category3.png "Category 3" | width=100)

Stock data can be deleted from the phone by pressing the trash can icon.
![Alt text](/screenshots/deletefile.png "Delete file" | width=100)

---


## Class diagram

This is a simplified class diagram which shows the overall structure. The code itself is the main documentation. This project has followed Android Architecture guide https://developer.android.com/jetpack/guide.

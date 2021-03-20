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

<img src=/screenshots/start.png width="300">

Add CSV files which contains stock data in a following way
“Date, Close/Last, Volume, Open, High, Low
01/19/2021, $127.83, 90757330, $127.78, $128.71, $126.938”
Sample data can be found from https://www.nasdaq.com/market-activity/stocks/aapl/historical.

Select a file.

<img src=/screenshots/selectfile.png width="300">

Name the data.

<img src=/screenshots/namefile.png width="300">

Analyze stock data by selecting an item from the list.

<img src=/screenshots/filedownloaded.png width="300">

Select a category and date range to analyze data.

<img src=/screenshots/category3.png width="300">

Stock data can be deleted from the phone by pressing the trash can icon.

<img src=/screenshots/deletefile.png width="300">

---

## Class diagram

This is a simplified class diagram which shows the overall structure. The code itself is the main documentation. This project has followed the Android Architecture guide https://developer.android.com/jetpack/guide.

<img src=/screenshots/diagram.png width="600">

This application uses a single Activity which is populated by StockListFragment and StockFragment.
Fragments are used for updating the UI. ViewModels are used for managing the data the fragments show.
StockRepository is used for separating the data source from the ViewModels. StockStorageHandler is used for 
to save, delete and download stock data from internal storage. StockFileReader is used for reading stock 
data from file. StockItem contains the filename (which is the stock items name and a file name in internal storage) 
and StockStatistics which contains stock data (date, open price, volume...). StockItemAnalyzer analyses the data the StockItem 
contains and answers the questions which are writen in Description. The program also contains some other classes for example
ArrayAdapters which are used for to populate lists and some smaller classes which are used for to capsulate data.


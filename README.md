# Scrooge McDucks App

---

### Table of Contents

- [Description](#description)
- [How To Use](#how-to-use)
- [Class diagram](#class-diagram)

---

## Description

Android application which analyzes stock data. User can import stock data in a CSV file and find out answers to the following questions.
- How many days was the longest bullish (upward) trend within a given date range?
- Which dates within a given date range had a) the highest trading volume and b) the most significant stock price change within a day?
- Within a given date range, which dates had the best opening price compared to 5 days simple moving average (SMA 5)?

 
#### Technologies

- Android studio
- Java
- Hilt
- Android Jetpack LiveData and ViewModels

---

## How To Use

#### Installation

This project has been created using Android studio. To install the project into a Android device, clone the project and open it in Android studio. This repository also contains .APK (Android package) file which can be used for to open the application in a Android device. Minimum Android version is Android 5.0.

#### Features

When the application is launched for the first time, GameStop sample data is imported into the application. This data can be used for testing.

<img src=/screenshots/start.png width="300">

To import data into the application, click the add button and select .CSV file which contain stock data in a following format:

Date, Close/Last, Volume, Open, High, Low

01/19/2021, $127.83, 90757330, $127.78, $128.71, $126.938

Data can be downloaded at https://www.nasdaq.com/market-activity/stocks/aapl/historical.

<img src=/screenshots/selectfile.png width="300">

Name the data.

<img src=/screenshots/namefile.png width="300">

Analyze stock data by selecting an item from the list.

<img src=/screenshots/filedownloaded.png width="300">

Select a category and date range to analyze data.

<img src=/screenshots/category3.png width="300">

Stock data can be deleted from the device by pressing the trash can icon.

<img src=/screenshots/deletefile.png width="300">

---

## Class diagram

This is a simplified class diagram which shows the overall structure. The code itself is the main documentation. This project has followed the Android Architecture guide https://developer.android.com/jetpack/guide.

<img src=/screenshots/diagram.png width="600">

This application uses a single Activity which is populated by StockListFragment and StockFragment. Classes have following jobs:
- Fragments update the UI. 
- ViewModels manage the data the fragments show. 
- StockRepositor separates the data source from the ViewModels. 
- StockStorageHandler saves, deletes and downloads stock data from internal storage. 
- StockFileReader reads stock data from a file. 
- StockItem contains the filename (which is the StockItem's name and a file name in internal storage) and StockStatistics which contain stock data (date, open price, volume...). 
- StockItemAnalyzer analyses the data the StockItem contain and answers the questions which are written in the Description. 

The project also has some other classes, for example ArrayAdapters which are used for to populate ListViews and some smaller classes which are used for to capsulate data. The project also has unit tests, which test that StockItemAnalyzer returns correct answers.


package com.example.stockanalyzer.stock;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class StockStatistic {
    public GregorianCalendar date;
    public double closePrice;
    public double volume;
    public double openPrice;
    public double highPrice;
    public double lowPrice;

    public StockStatistic(){}

    public StockStatistic(GregorianCalendar date, double closePrice, double volume, double openPrice, double highPrice, double lowPrice) {
        this.date = date;
        this.closePrice = closePrice;
        this.volume = volume;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
    }

    public StockStatistic setDate(GregorianCalendar date) {
        this.date = date;
        return this;
    }

    public StockStatistic setClosePrice(double closePrice) {
        this.closePrice = closePrice;
        return this;
    }

    public StockStatistic setVolume(double volume) {
        this.volume = volume;
        return this;
    }

    public StockStatistic setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
        return this;
    }

    public StockStatistic setHighPrice(double highPrice) {
        this.highPrice = highPrice;
        return this;
    }

    public StockStatistic setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
        return this;
    }
}

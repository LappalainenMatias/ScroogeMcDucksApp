package com.example.stockanalyzer.stock;

import java.util.GregorianCalendar;

public class TradingVolumeAndPriceChange {

    public GregorianCalendar gregorianCalendar;
    public double tradingVolume;
    public double priceChange;

    public TradingVolumeAndPriceChange(GregorianCalendar gregorianCalendar, double tradingVolume, double priceChange) {
        this.gregorianCalendar = gregorianCalendar;
        this.tradingVolume = tradingVolume;
        this.priceChange = priceChange;
    }
}

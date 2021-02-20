package com.example.stockanalyzer.stock;

import java.util.GregorianCalendar;

public class OpeningPriceSMA5 {
    public GregorianCalendar gregorianCalendar;
    public double SMA5;
    public double openingPrice;

    public OpeningPriceSMA5(GregorianCalendar gregorianCalendar, double SMA5, double openingPrice) {
        this.gregorianCalendar = gregorianCalendar;
        this.SMA5 = SMA5;
        this.openingPrice = openingPrice;
    }

    /**
     * @return percentage
     */
    public double getOpeningPriceAndSMA5Difference() {
        if (openingPrice == 0)
            return 0;
        return SMA5 / openingPrice;
    }
}

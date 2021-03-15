package com.example.stockanalyzer.stock;

import java.util.GregorianCalendar;

public class LongestUpwardTrend {

    public int length;
    public GregorianCalendar start;
    public GregorianCalendar end;

    /**
     * Upward trend means closing price of day N is higher than closing price of day N-1
     * @param length Amount of days the upward trend has lasted.
     *               This is NOT always equal to the days between start and end because
     *               stock is not active every day for example in the weekends.
     * @param start Start of the upward trend
     * @param end End of the upward trend
     */
    public LongestUpwardTrend(int length, GregorianCalendar start, GregorianCalendar end) {
        this.length = length;
        this.start = start;
        this.end = end;
    }
}

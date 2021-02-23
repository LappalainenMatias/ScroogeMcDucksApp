package com.example.stockanalyzer.stock;

import java.util.GregorianCalendar;

public class LongestUpwardTrend {
    public int size;
    public GregorianCalendar start;
    public GregorianCalendar end;

    public LongestUpwardTrend(int size, GregorianCalendar start, GregorianCalendar end) {
        this.size = size;
        this.start = start;
        this.end = end;
    }
}

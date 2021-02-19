package com.example.stockanalyzer.stock;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockItem {
    public int id;
    public String name;
    public GregorianCalendar rangeStart;
    public GregorianCalendar rangeEnd;
    public HashMap<GregorianCalendar, StockStatistic> stockStatisticByCalendar;

    public StockItem(int id, String name, GregorianCalendar rangeStart, GregorianCalendar rangeEnd,
                     HashMap<GregorianCalendar, StockStatistic> stockStatistics) {
        this.id = id;
        this.name = name;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.stockStatisticByCalendar = stockStatistics;
    }

    public StockItem(int id, String name, GregorianCalendar rangeStart, GregorianCalendar rangeEnd) {
        this.id = id;
        this.name = name;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.stockStatisticByCalendar = new HashMap<>();
    }
}

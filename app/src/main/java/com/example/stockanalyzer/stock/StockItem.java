package com.example.stockanalyzer.stock;

import java.util.GregorianCalendar;
import java.util.HashMap;

public class StockItem {
    public String fileName;
    public HashMap<GregorianCalendar, StockStatistic> stockStatisticByCalendar;

    public StockItem(String fileName, HashMap<GregorianCalendar, StockStatistic> stockStatistics) {
        this.fileName = fileName;
        this.stockStatisticByCalendar = stockStatistics;
    }
}

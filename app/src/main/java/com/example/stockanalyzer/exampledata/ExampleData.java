package com.example.stockanalyzer.exampledata;

import com.example.stockanalyzer.stock.StockItem;
import com.example.stockanalyzer.stock.StockStatistic;

import java.util.GregorianCalendar;
import java.util.HashMap;

public class ExampleData {

    /**
     * DO NOT EDIT THIS DATA! This data is currently used in tests.
     */
    public StockItem exampleStockItem1() {
        HashMap<GregorianCalendar, StockStatistic> stats = new HashMap<>();
        stats.put(new GregorianCalendar(2010, 0, 1),
                (new StockStatistic().setDate(new GregorianCalendar(2010, 0, 1))
                        .setClosePrice(135.37).setVolume(20000).setOpenPrice(135.50).setHighPrice(136.0).setLowPrice(133.2)));
        stats.put(new GregorianCalendar(2010, 0, 2),
                (new StockStatistic().setDate(new GregorianCalendar(2010, 0, 2))
                        .setClosePrice(135.13).setVolume(12000).setOpenPrice(135.20).setHighPrice(136.2).setLowPrice(133.1)));
        stats.put(new GregorianCalendar(2010, 0, 3),
                (new StockStatistic().setDate(new GregorianCalendar(2010, 0, 3))
                        .setClosePrice(135.39).setVolume(11000).setOpenPrice(135.9).setHighPrice(136.7).setLowPrice(134.2)));
        stats.put(new GregorianCalendar(2010, 0, 4),
                (new StockStatistic().setDate(new GregorianCalendar(2010, 0, 4))
                        .setClosePrice(136.01).setVolume(20000).setOpenPrice(136.1).setHighPrice(137.2).setLowPrice(135.1)));
        stats.put(new GregorianCalendar(2010, 0, 5),
                (new StockStatistic().setDate(new GregorianCalendar(2010, 0, 5))
                        .setClosePrice(136.91).setVolume(5000).setOpenPrice(136.92).setHighPrice(137.9).setLowPrice(135.2)));
        stats.put(new GregorianCalendar(2010, 0, 6),
                (new StockStatistic().setDate(new GregorianCalendar(2010, 0, 6))
                        .setClosePrice(136.76).setVolume(10000).setOpenPrice(136.99).setHighPrice(137.0).setLowPrice(135.5)));
        stats.put(new GregorianCalendar(2010, 0, 7),
                (new StockStatistic().setDate(new GregorianCalendar(2010, 0, 7))
                        .setClosePrice(137.39).setVolume(11000).setOpenPrice(137.20).setHighPrice(138.0).setLowPrice(135.2)));
        return new StockItem(1,"Apple", stats);
    }

    /**
     * DO NOT EDIT THIS DATA! This data is currently used in tests.
     */
    public StockItem exampleStockItem2() {
        HashMap<GregorianCalendar, StockStatistic> stats = new HashMap<>();
        stats.put(new GregorianCalendar(2021, 1, 13),
                (new StockStatistic().setDate(new GregorianCalendar(2021, 1, 13))
                        .setClosePrice(139.1).setVolume(15000).setOpenPrice(135.20).setHighPrice(136.0).setLowPrice(133.2)));
        stats.put(new GregorianCalendar(2021, 1, 14),
                (new StockStatistic().setDate(new GregorianCalendar(2021, 1, 14))
                        .setClosePrice(138.1).setVolume(18000).setOpenPrice(135.20).setHighPrice(136.2).setLowPrice(133.1)));
        stats.put(new GregorianCalendar(2021, 1, 15),
                (new StockStatistic().setDate(new GregorianCalendar(2021, 1, 15))
                        .setClosePrice(137.1).setVolume(1000).setOpenPrice(135.22).setHighPrice(136.7).setLowPrice(134.2)));
        stats.put(new GregorianCalendar(2021, 1, 16),
                (new StockStatistic().setDate(new GregorianCalendar(2021, 1, 16))
                        .setClosePrice(136.1).setVolume(21000).setOpenPrice(136.12).setHighPrice(137.2).setLowPrice(135.1)));
        stats.put(new GregorianCalendar(2021, 1, 17),
                (new StockStatistic().setDate(new GregorianCalendar(2021, 1, 17))
                        .setClosePrice(135.1).setVolume(5000).setOpenPrice(135.01).setHighPrice(137.9).setLowPrice(135.2)));
        stats.put(new GregorianCalendar(2021, 1, 18),
                (new StockStatistic().setDate(new GregorianCalendar(2021, 1, 18))
                        .setClosePrice(136.2).setVolume(4000).setOpenPrice(136.99).setHighPrice(137.0).setLowPrice(135.5)));
        stats.put(new GregorianCalendar(2021, 1, 23),
                (new StockStatistic().setDate(new GregorianCalendar(2021, 1, 23))
                        .setClosePrice(137.4).setVolume(1000).setOpenPrice(137.20).setHighPrice(138.0).setLowPrice(135.2)));
        return new StockItem(2,"GameStop", stats);
    }
}

package com.example.stockanalyzer;

import android.provider.CalendarContract;
import android.util.Log;


import androidx.core.util.Pair;

import com.example.stockanalyzer.stock.StockItem;
import com.example.stockanalyzer.stock.StockItemAnalyzer;
import com.example.stockanalyzer.stock.StockStatistic;

import org.junit.Test;
import org.junit.Assert;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class UnitTest {

    private HashMap<GregorianCalendar, StockStatistic> getStockStatistics_example1(){
        HashMap<GregorianCalendar, StockStatistic> stats = new HashMap<>();
        stats.put(new GregorianCalendar(2010, 0, 1),
                (new StockStatistic().setDate(new GregorianCalendar(2010, 0, 1))
                .setClosePrice(135.37).setVolume(10000).setOpenPrice(135.50).setHighPrice(136.0).setLowPrice(133.2)));
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
        return stats;
    }

    private HashMap<GregorianCalendar, StockStatistic> getStockStatistics_example2(){
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
        return stats;
    }

    @Test
    public void createStocksStatistics(){
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2000, 0, 1);
        double closePrize = 100.0;
        double volume = 200000;
        double open = 102.0;
        double high = 105.1;
        double low = 99.0;
        StockStatistic stockStatistic = new StockStatistic(gregorianCalendar, closePrize, volume, open, high, low);
        Assert.assertEquals(stockStatistic.date, gregorianCalendar);
        Assert.assertEquals(stockStatistic.closePrice, closePrize, 0);
        Assert.assertEquals(stockStatistic.volume, volume, 0);
        Assert.assertEquals(stockStatistic.openPrice, open, 0);
        Assert.assertEquals(stockStatistic.highPrice, high, 0);
        Assert.assertEquals(stockStatistic.lowPrice, low, 0);
    }

    @Test
    public void createStockItem(){
        int id = 0;
        String name = "Apple";
        GregorianCalendar rangeStart = new GregorianCalendar(2010, 0, 5);
        GregorianCalendar rangeEnd = new GregorianCalendar(2010, 0, 7);

        StockItem stockItem = new StockItem(id, name, rangeStart, rangeEnd);

        Assert.assertEquals(id, stockItem.id);
        Assert.assertEquals("Apple", stockItem.name);
        Assert.assertEquals(rangeStart.getTimeInMillis(), stockItem.rangeStart.getTimeInMillis());
        Assert.assertEquals(rangeEnd.getTimeInMillis(), stockItem.rangeEnd.getTimeInMillis());
    }

    @Test
    public void getLongestUpwardTrend_example1(){
        int id = 0;
        String name = "Apple";
        GregorianCalendar rangeStart = new GregorianCalendar(2010, 0, 1);
        GregorianCalendar rangeEnd = new GregorianCalendar(2010, 0, 30);
        HashMap<GregorianCalendar, StockStatistic> stockStatisticByCalendar = getStockStatistics_example1();

        StockItem stockItem = new StockItem(id, name, rangeStart, rangeEnd, stockStatisticByCalendar);
        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItem);
        Pair<Integer, Pair<GregorianCalendar, GregorianCalendar>> output =
                stockItemAnalyzer.getLongestUpwardTrend(stockItem.rangeStart, stockItem.rangeEnd);

        int upwardTrendLength = output.first;
        GregorianCalendar startOfLongestUpward = output.second.first;
        GregorianCalendar endOfLongestUpward = output.second.second;

        Assert.assertEquals(4, upwardTrendLength);
        Assert.assertEquals((new GregorianCalendar(2010, 0, 2).getTimeInMillis()), startOfLongestUpward.getTimeInMillis());
        Assert.assertEquals((new GregorianCalendar(2010, 0, 5).getTimeInMillis()), endOfLongestUpward.getTimeInMillis());
    }

    @Test
    public void getLongestUpwardTrend_example2(){
        int id = 0;
        String name = "Apple";
        GregorianCalendar rangeStart = new GregorianCalendar(2010, 0, 1);
        GregorianCalendar rangeEnd = new GregorianCalendar(2022, 0, 30);
        HashMap<GregorianCalendar, StockStatistic> stockStatisticByCalendar = getStockStatistics_example2();

        StockItem stockItem = new StockItem(id, name, rangeStart, rangeEnd, stockStatisticByCalendar);
        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItem);
        Pair<Integer, Pair<GregorianCalendar, GregorianCalendar>> output =
                stockItemAnalyzer.getLongestUpwardTrend(stockItem.rangeStart, stockItem.rangeEnd);

        int upwardTrendLength = output.first;
        GregorianCalendar startOfLongestUpward = output.second.first;
        GregorianCalendar endOfLongestUpward = output.second.second;

        Assert.assertEquals((new GregorianCalendar(2021, 1, 17).getTimeInMillis()), startOfLongestUpward.getTimeInMillis());
        Assert.assertEquals((new GregorianCalendar(2021, 1, 23).getTimeInMillis()), endOfLongestUpward.getTimeInMillis());
        Assert.assertEquals(3, upwardTrendLength);
    }

    @Test
    public void getLongestUpwardTrend_rangeEndBeforeRangeStart(){
        int id = 0;
        String name = "Apple";
        GregorianCalendar rangeStart = new GregorianCalendar(2025, 0, 1);
        GregorianCalendar rangeEnd = new GregorianCalendar(2010, 0, 30);
        HashMap<GregorianCalendar, StockStatistic> stockStatisticByCalendar = getStockStatistics_example2();

        StockItem stockItem = new StockItem(id, name, rangeStart, rangeEnd, stockStatisticByCalendar);
        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItem);
        Pair<Integer, Pair<GregorianCalendar, GregorianCalendar>> output =
                stockItemAnalyzer.getLongestUpwardTrend(stockItem.rangeStart, stockItem.rangeEnd);

        int upwardTrendLength = output.first;
        GregorianCalendar startOfLongestUpward = output.second.first;
        GregorianCalendar endOfLongestUpward = output.second.second;

        Assert.assertEquals(null , startOfLongestUpward);
        Assert.assertEquals( null, endOfLongestUpward);
        Assert.assertEquals(0, upwardTrendLength);
    }

    @Test
    public void getLongestUpwardTrend_statisticsNotInRange(){
        int id = 0;
        String name = "Apple";
        GregorianCalendar rangeStart = new GregorianCalendar(2010, 6, 1);
        GregorianCalendar rangeEnd = new GregorianCalendar(2010, 6, 2);
        HashMap<GregorianCalendar, StockStatistic> stockStatisticByCalendar = getStockStatistics_example2();

        StockItem stockItem = new StockItem(id, name, rangeStart, rangeEnd, stockStatisticByCalendar);
        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItem);
        Pair<Integer, Pair<GregorianCalendar, GregorianCalendar>> output =
                stockItemAnalyzer.getLongestUpwardTrend(stockItem.rangeStart, stockItem.rangeEnd);

        int upwardTrendLength = output.first;
        GregorianCalendar startOfLongestUpward = output.second.first;
        GregorianCalendar endOfLongestUpward = output.second.second;

        Assert.assertEquals(null , startOfLongestUpward);
        Assert.assertEquals( null, endOfLongestUpward);
        Assert.assertEquals(0, upwardTrendLength);
    }
}
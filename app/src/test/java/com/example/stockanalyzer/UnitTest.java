package com.example.stockanalyzer;

import com.example.stockanalyzer.exampledata.ExampleData;
import com.example.stockanalyzer.stock.LongestUpwardTrend;
import com.example.stockanalyzer.stock.OpeningPriceSMA5;
import com.example.stockanalyzer.stock.StockItem;
import com.example.stockanalyzer.stock.StockItemAnalyzer;
import com.example.stockanalyzer.stock.StockStatistic;
import com.example.stockanalyzer.stock.TradingVolumeAndPriceChange;

import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class UnitTest {

    @Test
    public void createStocksStatistics() {
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
    public void createStockItem() {
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
    public void getLongestUpwardTrend_exampleStockItem1() {
        ExampleData exampleData = new ExampleData();
        StockItem stockItem = exampleData.exampleStockItem1();

        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItem);
        LongestUpwardTrend output = stockItemAnalyzer.getLongestUpwardTrend(stockItem.rangeStart, stockItem.rangeEnd);

        Assert.assertEquals(4, output.size);
        Assert.assertEquals((new GregorianCalendar(2010, 0, 2).getTimeInMillis()), output.start.getTimeInMillis());
        Assert.assertEquals((new GregorianCalendar(2010, 0, 5).getTimeInMillis()), output.end.getTimeInMillis());
    }

    @Test
    public void getLongestUpwardTrend_exampleStockItem2() {
        ExampleData exampleData = new ExampleData();
        StockItem stockItem = exampleData.exampleStockItem2();
        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItem);
        LongestUpwardTrend output = stockItemAnalyzer.getLongestUpwardTrend(stockItem.rangeStart, stockItem.rangeEnd);

        Assert.assertEquals((new GregorianCalendar(2021, 1, 17).getTimeInMillis()), output.start.getTimeInMillis());
        Assert.assertEquals((new GregorianCalendar(2021, 1, 23).getTimeInMillis()), output.end.getTimeInMillis());
        Assert.assertEquals(3, output.size);
    }

    @Test
    public void getLongestUpwardTrend_rangeEndBeforeRangeStart() {
        ExampleData exampleData = new ExampleData();
        StockItem stockItem = exampleData.exampleStockItem2();

        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItem);
        LongestUpwardTrend output = stockItemAnalyzer.getLongestUpwardTrend(
                new GregorianCalendar(1997, 0, 0),
                new GregorianCalendar(1998, 0,0));

        Assert.assertNull(output.start);
        Assert.assertNull(output.end);
        Assert.assertEquals(0, output.size);
    }

    @Test
    public void getLongestUpwardTrend_statisticsNotInRange() {
        ExampleData exampleData = new ExampleData();
        StockItem stockItem = exampleData.exampleStockItem2();
        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItem);
        LongestUpwardTrend output = stockItemAnalyzer.getLongestUpwardTrend(
                new GregorianCalendar(1997, 0, 0),
                new GregorianCalendar(1998, 0,0));

        Assert.assertNull(output.start);
        Assert.assertNull(output.end);
        Assert.assertEquals(0, output.size);
    }

    @Test
    public void createTradingVolumeAndPriceChange() {
        TradingVolumeAndPriceChange tradingVolumeAndPriceChange =
                new TradingVolumeAndPriceChange(new GregorianCalendar(2000, 1, 2), 2000, 5.1);

        Assert.assertEquals(5.1, tradingVolumeAndPriceChange.priceChange, 0.001);
        Assert.assertEquals(2000, tradingVolumeAndPriceChange.tradingVolume, 0.001);
        Assert.assertEquals((new GregorianCalendar(2000, 1, 2)).getTimeInMillis(),
                tradingVolumeAndPriceChange.gregorianCalendar.getTimeInMillis());
    }

    @Test
    public void getHighestTradingVolumeAndMostSignificantStockPriceChange_exampleStockItem1() {
        ExampleData exampleData = new ExampleData();
        StockItem stockItem = exampleData.exampleStockItem1();
        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItem);

        ArrayList<TradingVolumeAndPriceChange> tradingVolumeAndPriceChanges =
                stockItemAnalyzer.getHighestTradingVolumesAndLargestPriceChanges(stockItem.rangeStart, stockItem.rangeEnd);

        TradingVolumeAndPriceChange first = tradingVolumeAndPriceChanges.get(0);
        TradingVolumeAndPriceChange second = tradingVolumeAndPriceChanges.get(1);
        TradingVolumeAndPriceChange last = tradingVolumeAndPriceChanges.get(tradingVolumeAndPriceChanges.size() - 1);

        Assert.assertEquals(new GregorianCalendar(2010, 0, 1).getTimeInMillis(), first.gregorianCalendar.getTimeInMillis());
        Assert.assertEquals(20000, first.tradingVolume, 0.001);
        Assert.assertEquals(0.13, first.priceChange, 0.001);

        Assert.assertEquals(new GregorianCalendar(2010, 0, 4).getTimeInMillis(), second.gregorianCalendar.getTimeInMillis());
        Assert.assertEquals(20000, second.tradingVolume, 0.001);
        Assert.assertEquals(0.09, second.priceChange, 0.001);

        Assert.assertEquals(new GregorianCalendar(2010, 0, 5).getTimeInMillis(), last.gregorianCalendar.getTimeInMillis());
        Assert.assertEquals(5000, last.tradingVolume, 0.001);
        Assert.assertEquals(0.01, last.priceChange, 0.001);
    }

    @Test
    public void getHighestTradingVolumeAndMostSignificantStockPriceChange_exampleStockItem2() {
        ExampleData exampleData = new ExampleData();
        StockItem stockItem = exampleData.exampleStockItem2();
        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItem);

        ArrayList<TradingVolumeAndPriceChange> tradingVolumeAndPriceChanges =
                stockItemAnalyzer.getHighestTradingVolumesAndLargestPriceChanges(stockItem.rangeStart, stockItem.rangeEnd);

        TradingVolumeAndPriceChange first = tradingVolumeAndPriceChanges.get(0);
        TradingVolumeAndPriceChange second = tradingVolumeAndPriceChanges.get(1);
        TradingVolumeAndPriceChange last = tradingVolumeAndPriceChanges.get(tradingVolumeAndPriceChanges.size() - 1);

        Assert.assertEquals(new GregorianCalendar(2021, 1, 16).getTimeInMillis(), first.gregorianCalendar.getTimeInMillis());
        Assert.assertEquals(21000, first.tradingVolume, 0.001);
        Assert.assertEquals(0.02, first.priceChange, 0.001);

        Assert.assertEquals(new GregorianCalendar(2021, 1, 14).getTimeInMillis(), second.gregorianCalendar.getTimeInMillis());
        Assert.assertEquals(18000, second.tradingVolume, 0.001);
        Assert.assertEquals(2.9, second.priceChange, 0.001);

        Assert.assertEquals(new GregorianCalendar(2021, 1, 23).getTimeInMillis(), last.gregorianCalendar.getTimeInMillis());
        Assert.assertEquals(1000, last.tradingVolume, 0.001);
        Assert.assertEquals(0.2, last.priceChange, 0.001);
    }

    @Test
    public void getHighestTradingVolumeAndMostSignificantStockPriceChange_dataOutOfRange() {
        GregorianCalendar rangeStart = new GregorianCalendar(1999, 6, 1);
        GregorianCalendar rangeEnd = new GregorianCalendar(1999, 9, 2);
        ExampleData exampleData = new ExampleData();
        StockItem stockItem = exampleData.exampleStockItem1();
        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItem);

        ArrayList<TradingVolumeAndPriceChange> tradingVolumeAndPriceChanges =
                stockItemAnalyzer.getHighestTradingVolumesAndLargestPriceChanges(rangeStart, rangeEnd);

        Assert.assertEquals(0, tradingVolumeAndPriceChanges.size());
    }

    @Test
    public void getOpeningPricesComparedToSMA5_exampleStockItem1() {
        ExampleData exampleData = new ExampleData();
        StockItem stockItem = exampleData.exampleStockItem1();
        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItem);

        ArrayList<OpeningPriceSMA5> openingPriceSMA5s = stockItemAnalyzer.getOpeningPricesComparedToSMA5(stockItem.rangeStart, stockItem.rangeEnd);
        OpeningPriceSMA5 first = openingPriceSMA5s.get(0);
        OpeningPriceSMA5 second = openingPriceSMA5s.get(1);

        Assert.assertEquals(2, openingPriceSMA5s.size());

        Assert.assertEquals(0.991545, first.getOpeningPriceAndSMA5Difference(), 0.0001);
        Assert.assertEquals(new GregorianCalendar(2010, 0, 7).getTimeInMillis(),
                first.gregorianCalendar.getTimeInMillis());

        Assert.assertEquals(new GregorianCalendar(2010, 0, 6).getTimeInMillis(),
                second.gregorianCalendar.getTimeInMillis());
        Assert.assertEquals(0.991036, second.getOpeningPriceAndSMA5Difference(), 0.0001);
    }

    @Test
    public void getOpeningPricesComparedToSMA5_exampleStockItem2() {
        ExampleData exampleData = new ExampleData();
        StockItem stockItem = exampleData.exampleStockItem2();
        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItem);

        ArrayList<OpeningPriceSMA5> openingPriceSMA5s = stockItemAnalyzer.getOpeningPricesComparedToSMA5(stockItem.rangeStart, stockItem.rangeEnd);
        OpeningPriceSMA5 first = openingPriceSMA5s.get(0);
        OpeningPriceSMA5 second = openingPriceSMA5s.get(1);

        Assert.assertEquals(2, openingPriceSMA5s.size());

        Assert.assertEquals(1.000802, first.getOpeningPriceAndSMA5Difference(), 0.0001);
        Assert.assertEquals(new GregorianCalendar(2021, 1, 18).getTimeInMillis(),
                first.gregorianCalendar.getTimeInMillis());

        Assert.assertEquals(new GregorianCalendar(2021, 1, 23).getTimeInMillis(),
                second.gregorianCalendar.getTimeInMillis());
        Assert.assertEquals(0.995043, second.getOpeningPriceAndSMA5Difference(), 0.0001);
    }

    @Test
    public void ClosingPriceSMA5() {
        OpeningPriceSMA5 openingPriceSMA5 = new OpeningPriceSMA5(
                new GregorianCalendar(2000, 0, 1),
                10.2,
                10.1);

        Assert.assertEquals(new GregorianCalendar(2000, 0, 1).getTimeInMillis(),
                openingPriceSMA5.gregorianCalendar.getTimeInMillis());
        Assert.assertEquals(10.2, openingPriceSMA5.SMA5, 0.001);
        Assert.assertEquals(1.0099, openingPriceSMA5.getOpeningPriceAndSMA5Difference(), 0.00001);
    }
}
package com.example.stockanalyzer.stock;


import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class StockItemAnalyzer {

    private final HashMap<GregorianCalendar, StockStatistic> stockStatisticByCalendar;

    public StockItemAnalyzer(StockItem stockItem) {
        this.stockStatisticByCalendar = stockItem.stockStatisticByCalendar;
    }

    /**
     * @return If statistics are out of the range then return new LongestUpwardTrend(0, null, null)
     */
    public LongestUpwardTrend getLongestUpwardTrend(
            GregorianCalendar rangeStart, GregorianCalendar rangeEnd) {
        List<StockStatistic> statistics = new ArrayList<>(stockStatisticByCalendar.values());
        List<StockStatistic> cutStatistics = cut(statistics, rangeStart, rangeEnd);
        Collections.sort(cutStatistics, new CalendarComparator());

        if (cutStatistics.size() == 0) {
            return new LongestUpwardTrend(0, null, null);
        }

        int index = 0;
        int longestUpwardTrendStartIndex = 0;
        int longestUpwardTrendEndIndex = 0;
        int currentUpwardTrendStart = 0;
        int currentUpwardTrendEnd = 0;
        double lastClosePrice = -1;

        for (StockStatistic stockStatistic : cutStatistics) {
            if (longestUpwardTrendEndIndex - longestUpwardTrendStartIndex < currentUpwardTrendEnd - currentUpwardTrendStart) {
                longestUpwardTrendStartIndex = currentUpwardTrendStart;
                longestUpwardTrendEndIndex = currentUpwardTrendEnd;
            }
            if (stockStatistic.closePrice < lastClosePrice) {
                currentUpwardTrendStart = index;
            }
            currentUpwardTrendEnd = index;
            index++;
            lastClosePrice = stockStatistic.closePrice;
        }
        if (longestUpwardTrendEndIndex - longestUpwardTrendStartIndex < currentUpwardTrendEnd - currentUpwardTrendStart) {
            longestUpwardTrendStartIndex = currentUpwardTrendStart;
            longestUpwardTrendEndIndex = currentUpwardTrendEnd;
        }

        int size = longestUpwardTrendEndIndex - longestUpwardTrendStartIndex + 1;
        return new LongestUpwardTrend(size, cutStatistics.get(longestUpwardTrendStartIndex).date, cutStatistics.get(longestUpwardTrendEndIndex).date);
    }

    /**
     * @return List of dates, volumes and price changes.
     * The list is ordered by volume and price change.
     * So if two dates have the same volume, the one with the more significant price change should come first.
     */
    public ArrayList<TradingVolumeAndPriceChange> getHighestTradingVolumesAndLargestPriceChanges(
            GregorianCalendar rangeStart, GregorianCalendar rangeEnd) {
        List<StockStatistic> statistics = new ArrayList<>(stockStatisticByCalendar.values());
        List<StockStatistic> cutStatistics = cut(statistics, rangeStart, rangeEnd);
        if (cutStatistics.size() == 0) {
            return new ArrayList<>();
        }
        ArrayList<TradingVolumeAndPriceChange> tradingVolumeAndPriceChanges = createTradingVolumeAndPriceChanges(cutStatistics);
        Collections.sort(tradingVolumeAndPriceChanges, new VolumeAndPriceChangeCompator());
        return tradingVolumeAndPriceChanges;
    }

    /**
     * SMA 5 is a average value of closing prices between days N-1 to N-5 for day N
     *
     * @return List of OpeningPriceSMA5 objects. List is ordered by the
     * difference between the opening price of the day and the calculated SMA 5 price of the day
     */
    public ArrayList<OpeningPriceSMA5> getOpeningPricesComparedToSMA5(GregorianCalendar rangeStart, GregorianCalendar rangeEnd) {
        List<StockStatistic> statistics = new ArrayList<>(stockStatisticByCalendar.values());
        List<StockStatistic> cutStatistics = cut(statistics, rangeStart, rangeEnd);
        Collections.sort(cutStatistics, new CalendarComparator());
        ArrayList<OpeningPriceSMA5> openingPriceSMA5s = getOpeningPriceSMA5s(cutStatistics);
        Collections.sort(openingPriceSMA5s, new OpeningPriceAndSMA5DifferenceComparator());
        return openingPriceSMA5s;
    }

    private ArrayList<OpeningPriceSMA5> getOpeningPriceSMA5s(List<StockStatistic> statistics) {
        ArrayList<OpeningPriceSMA5> openingPriceSMA5s = new ArrayList<>();
        if (statistics.size() < 6)
            return new ArrayList<>();

        for (int index = 5; index < statistics.size(); index++) {
            double last5ClosePriceSum = statistics.get(index - 5).closePrice + statistics.get(index - 4).closePrice +
                    statistics.get(index - 3).closePrice + statistics.get(index - 2).closePrice + statistics.get(index - 1).closePrice;
            double SMA5 = last5ClosePriceSum / 5;
            openingPriceSMA5s.add(new OpeningPriceSMA5(statistics.get(index).date, SMA5, statistics.get(index).openPrice));
        }
        return openingPriceSMA5s;
    }

    private List<StockStatistic> cut(List<StockStatistic> stockStatistics, GregorianCalendar rangeStart,
                                     GregorianCalendar rangeEnd) {
        List<StockStatistic> cutted = new ArrayList<>();
        for (StockStatistic stockStatistic : stockStatistics) {
            GregorianCalendar gregorianCalendar = stockStatistic.date;
            if (dateIsBetweenDates(gregorianCalendar, rangeStart, rangeEnd)) {
                cutted.add(stockStatistic);
            }
        }
        return cutted;
    }

    // TODO: 19.2.2021 Refactor dates to calendar

    /**
     * Start and end of the range are part of the range. This means that
     * if date == rangeStart or date == rangeEnd method return true.
     */
    private boolean dateIsBetweenDates(GregorianCalendar date, GregorianCalendar rangeStart, GregorianCalendar rangeEnd) {
        return rangeStart.getTimeInMillis() <= date.getTimeInMillis() && date.getTimeInMillis() <= rangeEnd.getTimeInMillis();
    }

    private ArrayList<TradingVolumeAndPriceChange> createTradingVolumeAndPriceChanges(List<StockStatistic> cutStatistics) {
        ArrayList<TradingVolumeAndPriceChange> tradingVolumesAndPriceChanges = new ArrayList<>();
        for (StockStatistic stockStatistic : cutStatistics) {
            tradingVolumesAndPriceChanges.add(new TradingVolumeAndPriceChange(stockStatistic.date,
                    stockStatistic.volume, Math.abs(stockStatistic.closePrice - stockStatistic.openPrice)));
        }
        return tradingVolumesAndPriceChanges;
    }

    private static class OpeningPriceAndSMA5DifferenceComparator implements Comparator<OpeningPriceSMA5> {
        @Override
        public int compare(OpeningPriceSMA5 o1, OpeningPriceSMA5 o2) {
            return Double.compare(o2.getOpeningPriceAndSMA5Difference(), o1.getOpeningPriceAndSMA5Difference());
        }
    }

    private static class VolumeAndPriceChangeCompator implements Comparator<TradingVolumeAndPriceChange> {
        @Override
        public int compare(TradingVolumeAndPriceChange o1, TradingVolumeAndPriceChange o2) {
            if (o1.tradingVolume < o2.tradingVolume) {
                return 1;
            }
            if (o1.tradingVolume == o2.tradingVolume) {
                return Double.compare(o2.priceChange, o1.priceChange);
            }
            return -1;
        }
    }

    private static class CalendarComparator implements Comparator<StockStatistic> {
        @Override
        public int compare(StockStatistic o1, StockStatistic o2) {
            return Long.compare(o1.date.getTimeInMillis(), o2.date.getTimeInMillis());
        }
    }
}


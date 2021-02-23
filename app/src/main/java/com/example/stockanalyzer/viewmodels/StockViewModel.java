package com.example.stockanalyzer.viewmodels;

import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.stockanalyzer.stock.LongestUpwardTrend;
import com.example.stockanalyzer.stock.OpeningPriceSMA5;
import com.example.stockanalyzer.stock.StockItem;
import com.example.stockanalyzer.stock.StockItemAnalyzer;
import com.example.stockanalyzer.stock.TradingVolumeAndPriceChange;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

public class StockViewModel extends ViewModel {

    MutableLiveData<Calendar> rangeStart;
    MutableLiveData<Calendar> rangeEnd;
    MutableLiveData<List<String>> categories;
    MutableLiveData<String> selectedCategory;
    MutableLiveData<StockItem> stockItem;
    MutableLiveData<String> answer;
    MutableLiveData<LongestUpwardTrend> longestUpwardTrend;
    MutableLiveData<List<TradingVolumeAndPriceChange>> tradingVolumesAndPriceChanges;
    MutableLiveData<List<OpeningPriceSMA5>> openingPriceSMA5s;
    StockItemAnalyzer stockItemAnalyzer;

    public MutableLiveData<List<OpeningPriceSMA5>> getOpeningPriceSMA5s() {
        if (openingPriceSMA5s == null) {
            openingPriceSMA5s = new MutableLiveData<List<OpeningPriceSMA5>>();
        }
        return openingPriceSMA5s;
    }

    public MutableLiveData<List<TradingVolumeAndPriceChange>> getSTradingVolumesAndPriceChanges() {
        if (tradingVolumesAndPriceChanges == null) {
            tradingVolumesAndPriceChanges = new MutableLiveData<List<TradingVolumeAndPriceChange>>();
        }
        return tradingVolumesAndPriceChanges;
    }

    public MutableLiveData<LongestUpwardTrend> getLongestUpwardTrend() {
        if (longestUpwardTrend == null) {
            longestUpwardTrend = new MutableLiveData<LongestUpwardTrend>();
        }
        return longestUpwardTrend;
    }

    public MutableLiveData<String> getAnswer() {
        if (answer == null) {
            answer = new MutableLiveData<String>();
        }
        return answer;
    }

    public MutableLiveData<StockItem> getCategories() {
        if (categories == null) {
            categories = new MutableLiveData<List<String>>();
            loadCategories();
        }
        return stockItem;
    }

    private void loadCategories() {
        // TODO: 23/02/2021
    }

    public MutableLiveData<StockItem> getStockItem() {
        if (stockItem == null) {
            stockItem = new MutableLiveData<StockItem>();
            loadStockItem();
            loadStockItemAnalyzer();
        }
        return stockItem;
    }

    public MutableLiveData<Calendar> getRangeStart() {
        if (rangeStart == null) {
            rangeStart = new MutableLiveData<Calendar>();
            loadRangeStart();
        }
        return rangeStart;
    }

    public MutableLiveData<Calendar> getRangeEnd() {
        if (rangeEnd == null) {
            rangeEnd = new MutableLiveData<Calendar>();
            loadRangeEnd();
        }
        return rangeEnd;
    }

    public MutableLiveData<String> getSelectedCategory() {
        if (selectedCategory == null) {
            selectedCategory = new MutableLiveData<String>();
            loadCategory();
        }
        return selectedCategory;
    }

    /**
     * @return true if user has inputted all the necessary data to return results.
     */
    public boolean analyze(){
        return false;
    }

    private void loadStockItem() {
    }

    private void loadRangeStart() {
        rangeStart.setValue(Objects.requireNonNull(stockItem.getValue()).rangeStart);
    }

    private void loadRangeEnd() {
        rangeStart.setValue(Objects.requireNonNull(stockItem.getValue()).rangeEnd);
    }

    private void loadCategory() {
    }

    private void loadStockItemAnalyzer() {
        stockItemAnalyzer = new StockItemAnalyzer(stockItem.getValue());
    }
}

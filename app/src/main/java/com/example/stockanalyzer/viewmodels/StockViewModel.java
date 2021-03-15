package com.example.stockanalyzer.viewmodels;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.stockanalyzer.repository.StockRepository;
import com.example.stockanalyzer.stock.LongestUpwardTrend;
import com.example.stockanalyzer.stock.OpeningPriceSMA5;
import com.example.stockanalyzer.stock.StockItem;
import com.example.stockanalyzer.stock.StockItemAnalyzer;
import com.example.stockanalyzer.stock.TradingVolumeAndPriceChange;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ViewModelScoped;

@ViewModelScoped
public class StockViewModel extends ViewModel {

    MutableLiveData<Pair<GregorianCalendar, GregorianCalendar>> dateRange;
    MutableLiveData<String> stockFileName;
    MutableLiveData<List<String>> categories;
    MutableLiveData<String> selectedCategory;
    MutableLiveData<StockItem> stockItem;
    MutableLiveData<String> answer;
    MutableLiveData<List<TradingVolumeAndPriceChange>> tradingVolumesAndPriceChanges;
    MutableLiveData<List<OpeningPriceSMA5>> openingPriceSMA5s;
    StockItemAnalyzer stockItemAnalyzer;

    @Inject
    StockRepository stockRepository;

    public StockViewModel(String stockFileName) {
        this.stockFileName = new MutableLiveData<>(stockFileName);
        stockItemAnalyzer = new StockItemAnalyzer(getStockItem().getValue());
    }

    public MutableLiveData<Pair<GregorianCalendar, GregorianCalendar>> getDateRange() {
        if (dateRange == null) {
            dateRange = new MutableLiveData<>();
            loadDateRange();
        }
        return dateRange;
    }

    public MutableLiveData<String> getStockFileName() {
        if (stockFileName == null) {
            stockFileName = new MutableLiveData<>(getStockItem().getValue().fileName);
        }
        return stockFileName;
    }

    public MutableLiveData<List<OpeningPriceSMA5>> getOpeningPriceSMA5s() {
        if (openingPriceSMA5s == null) {
            openingPriceSMA5s = new MutableLiveData<>();
        }
        return openingPriceSMA5s;
    }

    public MutableLiveData<List<TradingVolumeAndPriceChange>> getTradingVolumesAndPriceChanges() {
        if (tradingVolumesAndPriceChanges == null) {
            tradingVolumesAndPriceChanges = new MutableLiveData<>();
        }
        return tradingVolumesAndPriceChanges;
    }

    public MutableLiveData<String> getAnswer() {
        if (answer == null) {
            answer = new MutableLiveData<String>();
            answer.setValue("");
        }
        return answer;
    }

    public MutableLiveData<List<String>> getCategories() {
        if (categories == null) {
            categories = new MutableLiveData<List<String>>();
            loadCategories();
        }
        return categories;
    }

    private void loadCategories() {
        List<String> items = new ArrayList<>();
        items.add("Longest upward trend");
        items.add("Highest trading volume and the most significant stock price change");
        items.add("Best opening price compared to 5 day moving average");
        categories.setValue(items);
    }

    public MutableLiveData<StockItem> getStockItem() {
        if (stockItem == null) {
            stockItem = new MutableLiveData<>();
            loadStockItem();
        }
        return stockItem;
    }

    public MutableLiveData<String> getSelectedCategory() {
        if (selectedCategory == null) {
            selectedCategory = new MutableLiveData<String>();
            loadSelectedCategory();
        }
        return selectedCategory;
    }

    public void analyze() {
        switch (getSelectedCategory().getValue()) {
            // TODO: 25/02/2021 Use R.string instead of fixed value.
            case "Longest upward trend":
                analyze_longestUpwardTrend();
                break;
            case "Highest trading volume and the most significant stock price change":
                analyze_highestTradingVolumeAnsMostSignificantStockChange();
                break;
            case "Best opening price compared to 5 day moving average":
                analyze_openingPriceComparedToSMA5();
                break;
        }
    }

    private void loadStockItem() {
        stockItem = new MutableLiveData<>(stockRepository.getStockItem(getStockFileName().getValue()));
    }

    private void loadSelectedCategory() {
        selectedCategory = new MutableLiveData<>("Longest upward trend");
    }

    private void loadDateRange() {
        GregorianCalendar earliest = null;
        GregorianCalendar latest = null;
        for (GregorianCalendar gregorianCalendar : getStockItem().getValue().stockStatisticByCalendar.keySet()) {
            if (earliest == null || earliest.getTimeInMillis() > gregorianCalendar.getTimeInMillis()) {
                earliest = gregorianCalendar;
            }
            if (latest == null || latest.getTimeInMillis() < gregorianCalendar.getTimeInMillis()) {
                latest = gregorianCalendar;
            }
        }
        dateRange.setValue(new Pair<>(earliest, latest));
    }

    // TODO: 25/02/2021 Create this so that it supports multiple languages
    private void analyze_longestUpwardTrend() {
        LongestUpwardTrend longestUpwardTrend =
                stockItemAnalyzer.getLongestUpwardTrend(getDateRange().getValue().first, getDateRange().getValue().second);
        int length = longestUpwardTrend.length;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        if (length < 2) {
            androidx.core.util.Pair<GregorianCalendar, GregorianCalendar> dateRange = stockItemAnalyzer.getStocksDateRange();
            getAnswer().setValue("Date range did not contain upward trend. " + getStockFileName().getValue()
                    + " stock data starts <b>" + sdf.format(dateRange.first.getTime())
                    + "</b> and ends <b>" + sdf.format(dateRange.second.getTime()) + "</b>.");
        } else {
            GregorianCalendar start = longestUpwardTrend.start;
            GregorianCalendar end = longestUpwardTrend.end;
            getAnswer().setValue("In " + getStockFileName().getValue() + " stock historical data the Close/Last price increased <b>"
                    + length + "</b> days in a row between <b>" + sdf.format(start.getTime())
                    + "</b> and <b>" + sdf.format(end.getTime()) + "</b>.");
        }
        getTradingVolumesAndPriceChanges().setValue(new ArrayList<>());//Hides list view
    }

    private void analyze_highestTradingVolumeAnsMostSignificantStockChange() {
        List<TradingVolumeAndPriceChange> tradingVolumeAndPriceChanges =
                stockItemAnalyzer.getHighestTradingVolumesAndLargestPriceChanges(
                        getDateRange().getValue().first, getDateRange().getValue().second);


        if (tradingVolumeAndPriceChanges.size() < 1) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            androidx.core.util.Pair<GregorianCalendar, GregorianCalendar> dateRange = stockItemAnalyzer.getStocksDateRange();
            getAnswer().setValue("Date range did not contain enough data. " + getStockFileName().getValue()
                    + " stock data starts <b>" + sdf.format(dateRange.first.getTime())
                    + "</b> and ends <b>" + sdf.format(dateRange.second.getTime()) + "</b>.");
        } else {
            getAnswer().setValue("");
        }
        getTradingVolumesAndPriceChanges().setValue(tradingVolumeAndPriceChanges);
    }

    private void analyze_openingPriceComparedToSMA5() {
        List<OpeningPriceSMA5> openingPriceSMA5s = stockItemAnalyzer.getOpeningPricesComparedToSMA5(
                getDateRange().getValue().first, getDateRange().getValue().second);

        if (openingPriceSMA5s.size() < 1) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            androidx.core.util.Pair<GregorianCalendar, GregorianCalendar> dateRange = stockItemAnalyzer.getStocksDateRange();
            getAnswer().setValue("Date range did not contain enough data. " + getStockFileName().getValue()
                    + " stock data starts <b>" + sdf.format(dateRange.first.getTime())
                    + "</b> and ends <b>" + sdf.format(dateRange.second.getTime()) + "</b>.");
        } else {
            getAnswer().setValue("");
        }
        getOpeningPriceSMA5s().setValue(openingPriceSMA5s);
    }
}

package com.example.stockanalyzer.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.stockanalyzer.repository.Repository;
import com.example.stockanalyzer.stock.OpeningPriceSMA5;
import com.example.stockanalyzer.stock.StockItem;

import java.util.List;

public class StockListViewModel extends ViewModel {
    MutableLiveData<List<StockItem>> stockItems;

    public MutableLiveData<List<StockItem>> getStockItems() {
        if (stockItems == null) {
            stockItems = new MutableLiveData<List<StockItem>>();
            loadStockItems();
        }
        return stockItems;
    }

    private void loadStockItems() {
        Repository repository = new Repository();
        stockItems.setValue(repository.getStocks());
    }
}
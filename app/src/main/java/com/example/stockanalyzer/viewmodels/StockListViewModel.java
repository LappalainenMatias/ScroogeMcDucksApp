package com.example.stockanalyzer.viewmodels;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.stockanalyzer.repository.StockRepository;
import com.example.stockanalyzer.stock.StockItem;

import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class StockListViewModel extends ViewModel {

    MutableLiveData<List<StockItem>> stockItems;
    MutableLiveData<Uri> selectedFileUri;

    StockRepository stockRepository;

    @Inject
    public StockListViewModel(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    public MutableLiveData<List<StockItem>> getStockItems() {
        if (stockItems == null) {
            stockItems = new MutableLiveData<List<StockItem>>();
            loadStockItems();
        }
        return stockItems;
    }

    /**
     * Uri for the file the user is saving into the internal storage.
     * Null if no file has been selected
     */
    public MutableLiveData<Uri> getSelectedFileUri() {
        if(selectedFileUri == null){
            selectedFileUri = new MutableLiveData<>(null);
        }
        return selectedFileUri;
    }

    /**
     * @param fileName this is also the name for the stock which is shown to the user
     * @return true if the file was saved successfully
     */
    public boolean saveStockDataFile(String fileName) {
        if(getSelectedFileUri().getValue() == null){
            return false;
        }
        boolean fileSaved = stockRepository.saveFile(getSelectedFileUri().getValue(), fileName);
        loadStockItems();
        return fileSaved;
    }

    public void loadStockItems() {
        getStockItems().setValue(stockRepository.getStockItems());
    }
}
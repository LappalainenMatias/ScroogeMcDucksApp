package com.example.stockanalyzer.repository;

import android.net.Uri;

import com.example.stockanalyzer.stock.StockItem;
import com.example.stockanalyzer.storage.StockStorageHandler;
import java.util.ArrayList;

import javax.inject.Inject;

public class StockRepository {

    StockStorageHandler stockStorageHandler;

    @Inject
    public StockRepository(StockStorageHandler stockStorageHandler){
        this.stockStorageHandler = stockStorageHandler;
    }

    /**
     * @return False if there was a problem saving the file. For example,
     * file type or the data in a file is incorrect.
     */
    public boolean saveFile(Uri uri, String fileName){
        return stockStorageHandler.saveFile(uri, fileName);
    }

    /**
     * @return False if file was not deleted
     */
    public boolean deleteFile(String filename){
        return stockStorageHandler.deleteFile(filename);
    }

    public ArrayList<StockItem> getStockItems(){
        return stockStorageHandler.getStockItems();
    }

    public StockItem getStockItem(String fileName){
        return stockStorageHandler.getStockItem(fileName);
    }
}

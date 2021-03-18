package com.example.stockanalyzer.filereader;

import android.content.Context;

import com.example.stockanalyzer.stock.StockItem;

import java.io.File;

/**
 * Reads stock data from file.
 */
public interface StockFileReader {
    /**
     * @param pathName path for the file. Often /data/data/<YOUR_APP_PACKAGE_NAME>/files/<FILES_NAME>
     * @param stockName Name which is assigned to the StockItem which is returned
     * @param context
     * @return
     */
    StockItem getStockItem(String pathName, String stockName, Context context);
}

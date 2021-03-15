package com.example.stockanalyzer.filereader;

import android.content.Context;

import com.example.stockanalyzer.stock.StockItem;

import java.io.File;

/**
 * Reads stock data from file.
 */
public interface StockFileReader {
    StockItem getStockItem(String fileName, Context context);
}

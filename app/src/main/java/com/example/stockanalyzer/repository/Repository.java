package com.example.stockanalyzer.repository;

import com.example.stockanalyzer.exampledata.ExampleData;
import com.example.stockanalyzer.stock.StockItem;

import java.util.ArrayList;

public class Repository {
    public ArrayList<StockItem> getStocks(){
        ArrayList<StockItem> stockItems = new ArrayList<>();
        ExampleData exampleData = new ExampleData();
        stockItems.add(exampleData.exampleStockItem1());
        stockItems.add(exampleData.exampleStockItem2());
        return stockItems;
    }

    public StockItem getStock(int id){
        ArrayList<StockItem> stockItems = getStocks();
        for(StockItem stockItem : stockItems){
            if(stockItem.id == id){
                return stockItem;
            }
        }
        return null;
    }
}

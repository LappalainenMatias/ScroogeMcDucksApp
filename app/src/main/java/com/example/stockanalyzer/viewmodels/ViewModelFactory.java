package com.example.stockanalyzer.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.stockanalyzer.repository.StockRepository;

import java.io.FileOutputStream;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    StockRepository stockRepository;
    String fileName = "";

    @Inject
    public ViewModelFactory(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new StockViewModel(fileName, stockRepository);
    }
}

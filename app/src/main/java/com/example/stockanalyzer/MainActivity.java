package com.example.stockanalyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.stockanalyzer.repository.StockRepository;
import com.example.stockanalyzer.storage.StockStorageHandler;
import com.example.stockanalyzer.ui.StockFragment;
import com.example.stockanalyzer.ui.StockListFragment;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    StockRepository stockRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        SharedPreferences sharedPreferences = this.getSharedPreferences("data", MODE_PRIVATE);
        boolean firstAppLaunch = sharedPreferences.getBoolean("firstLaunch", true);

        // This is used for testing. At the first app launch game stop stoCk data is
        // stored into internal storage.
        if(firstAppLaunch){
            sharedPreferences.edit().putBoolean("firstLaunch", false).apply();
            saveGameStopDataToInternalStorage();
        }
    }

    private void saveGameStopDataToInternalStorage() {
        AssetManager assetManager = this.getAssets();
        try {
            InputStream stream = assetManager.open("gamestop.csv");
            stockRepository.saveFile(stream, "GameStop");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package com.example.stockanalyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.stockanalyzer.ui.StockFragment;
import com.example.stockanalyzer.ui.StockListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, StockFragment.newInstance())
                    .commitNow();
        }
    }
}
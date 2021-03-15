package com.example.stockanalyzer.filereader;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.stockanalyzer.stock.StockItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.inject.Inject;

public class StockCSVReader implements StockFileReader {

    public StockItem getStockItem(String fileName, Context context) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new
                    File(context.getFilesDir() + File.separator + "stockdata" + File.separator + fileName + ".csv")));
            String read;
            StringBuilder builder = new StringBuilder("");

            while ((read = bufferedReader.readLine()) != null) {
                builder.append(read);
            }
            Log.d("Output", builder.toString());
            bufferedReader.close();
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}

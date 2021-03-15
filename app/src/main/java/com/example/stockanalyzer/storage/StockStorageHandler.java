package com.example.stockanalyzer.storage;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.filereader.StockCSVReader;
import com.example.stockanalyzer.filereader.StockFileReader;
import com.example.stockanalyzer.stock.StockItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class StockStorageHandler {

    Context context;
    private final String folderName = "stockdata";

    @Inject
    public StockStorageHandler(@ApplicationContext Context context) {
        this.context = context;
    }

    /**
     * @return False if there was a problem saving the file. For example, file type or the data in a file was
     * incorrect
     */
    public boolean saveFile(Uri uri, String fileName) {
        File dir = new File(context.getFilesDir(), folderName);
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }

            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(context.openFileOutput(folderName + "/" + fileName + ".csv", Context.MODE_PRIVATE));
            outputStreamWriter.write(total.toString());
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            return false;
        }
        return true;
    }

    /**
     * @return False if file was not deleted
     */
    public boolean deleteFile(String filename) {
        File dir = new File(context.getFilesDir(), folderName);
        if (!dir.exists()) {
            return false;
        }
        File file = new File(dir, filename);
        return file.delete();
    }

    public ArrayList<StockItem> getStockItems() {
        ArrayList<StockItem> stockItems = new ArrayList<>();
        List<String> fileNames = getFileNames(folderName);
        for (String fileName : fileNames) {
            StockItem stockItem = getStockItem(fileName);
            if (stockItem != null) {
                stockItems.add(getStockItem(fileName));
            }
        }
        return stockItems;
    }

    private List<String> getFileNames(String folderName) {
        List<String> fileNames = new ArrayList<>();
        File path = new File(context.getFilesDir().getPath() + "/" + folderName);
        File list[] = path.listFiles();
        if (list == null || list.length == 0) {
            return fileNames;
        }
        for (int i = 0; i < list.length; i++) {
            fileNames.add(list[i].getName());
        }
        return fileNames;
    }

    public StockItem getStockItem(String fileName) {
        StockFileReader fileReader = new StockCSVReader();
//        String fileFormat = context.getContentResolver().getType(uri);
//        if ("csv".equals(fileFormat)) {
//            fileReader = new StockCSVReader();
//        } else {
//            return null;
//        }
        return fileReader.getStockItem(fileName, context);
    }

    /**
     * @return file type. For example csv, txt ...
     */
    private String getFileFormat(File file) {
        String[] parts = file.getName().split(".");
        if (parts.length < 2) {
            return null;
        }
        return parts[parts.length - 1];
    }

    public String getPathFromURI(Context context, Uri contentUri) {
        Cursor mediaCursor = null;
        try {
            String[] dataPath = { MediaStore.Images.Media.DATA };
            mediaCursor = context.getContentResolver().query(contentUri,  dataPath, null, null, null);
            int column_index = mediaCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            mediaCursor.moveToFirst();
            return mediaCursor.getString(column_index);
        } finally {
            if (mediaCursor != null) {
                mediaCursor.close();
            }
        }
    }
}

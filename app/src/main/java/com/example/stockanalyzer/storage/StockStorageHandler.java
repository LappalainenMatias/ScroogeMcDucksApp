package com.example.stockanalyzer.storage;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.stockanalyzer.filereader.StockCSVReader;
import com.example.stockanalyzer.filereader.StockFileReader;
import com.example.stockanalyzer.stock.StockItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class StockStorageHandler {

    Context context;

    @Inject
    public StockStorageHandler(@ApplicationContext Context context) {
        this.context = context;
    }

    /**
     * @return False if there was a problem saving the file.
     * File type or the data in a file was incorrect. File with same name already exists.
     * File name contains invalid characters.
     */
    public boolean saveFile(Uri uri, String fileName) {
        if(fileExists(fileName) || fileNameInvalid(fileName)){
            return false;
        }
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            return saveFile(inputStream, fileName);
        } catch (IOException ignored) {
        }
        return false;
    }

    /**
     * @return False if there was a problem saving the file.
     * File type or the data in a file was incorrect. File with same name already exists.
     * File name contains path separators.
     */
    public boolean saveFile(InputStream inputStream, String fileName) {
        if(fileExists(fileName) || fileNameInvalid(fileName)){
            return false;
        }

        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(
                    fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(total.toString());
            outputStreamWriter.close();

            //Test that file contains data which can be read
            //Delete file if it could not be read so that unnecessary files
            //are not stored in internal storage
            StockItem stockItem = getStockItem(fileName);
            if(stockItem == null){
                deleteFile(fileName);
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean fileExists(String fileName) {
        List<String> fileNames = getFileNames();
        for(String name : fileNames){
            if(name.equals(fileName)){
                return true;
            }
        }
        return false;
    }

    /**
     * @return True if file was deleted
     */
    public boolean deleteFile(String filename) {
        File file = new File(context.getFilesDir(), filename);
        return file.delete();
    }

    public ArrayList<StockItem> getStockItems() {
        ArrayList<StockItem> stockItems = new ArrayList<>();
        List<String> fileNames = getFileNames();
        for (String fileName : fileNames) {
            StockItem stockItem = getStockItem(fileName);
            if (stockItem != null) {
                stockItems.add(stockItem);
            }
        }
        return stockItems;
    }

    public StockItem getStockItem(String fileName) {
        StockFileReader fileReader = new StockCSVReader();
        StockItem stockItem = fileReader.getStockItem(
                context.getFilesDir() + File.separator + fileName, fileName, context);
        if(stockItem == null){
            return null;
        }
        if(stockItem.stockStatisticByCalendar.size() == 0){
            return null;
        }
        return stockItem;
    }

    private boolean fileNameInvalid(String fileName) {
        String invalidCharacters = "[\\\\/:*?\"<>|]";
        for(Character c : fileName.toCharArray()){
            if(invalidCharacters.indexOf(c) != -1){
                return true;
            }
        }
        return false;
    }

    private List<String> getFileNames() {
        List<String> fileNames = new ArrayList<>();
        File path = new File(context.getFilesDir().getPath());
        File[] list = path.listFiles();
        for (File file : list) {
            fileNames.add(file.getName());
        }
        return fileNames;
    }
}

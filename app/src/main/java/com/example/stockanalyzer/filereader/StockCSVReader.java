package com.example.stockanalyzer.filereader;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.stockanalyzer.stock.StockItem;
import com.example.stockanalyzer.stock.StockStatistic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.GregorianCalendar;

import javax.inject.Inject;

public class StockCSVReader implements StockFileReader {

    /**
     * @return null if data could not be read
     */
    public StockItem getStockItem(String pathName, String stockName, Context context) {
        StockItem stockItem = new StockItem(stockName, null);
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(new File(pathName)));

            /**
             * First line of the csv file tells how the data is formatted
             * for example, "Date, Close/Last, Volume, Open, High, Low"
             * Currently App supports only this data format.
             */
            String dataFormat = "Date, Close/Last, Volume, Open, High, Low";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                StockStatistic stockStatistic = getStockStatistic(dataFormat, line);
                if(stockStatistic != null){
                    stockItem.stockStatisticByCalendar.put(stockStatistic.date, stockStatistic);
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            return null;
        }
        return stockItem;
    }

    /**
     * @param dataFormat Tells how the data is formatted for example, "Date, Close/Last, Volume, Open, High, Low".
     * @param data For example, "01/19/2021, $127.83, 90757330, $127.78, $128.71, $126.938".
     * @return null if data could not been read.
     */
    public StockStatistic getStockStatistic(String dataFormat, String data){
        switch (dataFormat){
            case "Date, Close/Last, Volume, Open, High, Low":
                return getStockStatistic_Date_CloseLast_Volume_Open_High_Low(data);
        }
        return null;
    }

    /**
     * Data input example
     * “Date, Close/Last, Volume, Open, High, Low
     * 01/19/2021, $127.83, 90757330, $127.78, $128.71, $126.938”.
     * @return null if data could not be read.
     */
    private StockStatistic getStockStatistic_Date_CloseLast_Volume_Open_High_Low(String data) {
        try {
            String parts[] = data.split(",");

            String[] month_day_year = parts[0].split("/");
            String close_text = parts[1];
            String volume_text = parts[2];
            String open_text = parts[3];
            String high_text = parts[4];
            String low_text = parts[5];

            //Month 0 is january. Because of that, 1 is reduced from months
            GregorianCalendar date = new GregorianCalendar(
                    Integer.parseInt(month_day_year[2]),
                    Integer.parseInt(month_day_year[0]) - 1,
                    Integer.parseInt(month_day_year[1]));
            double closePrice = Double.parseDouble(close_text.replace("$", ""));
            double volume = Double.parseDouble(volume_text);
            double open = Double.parseDouble(open_text.replace("$", ""));
            double high = Double.parseDouble(high_text.replace("$", ""));
            double low = Double.parseDouble(low_text.replace("$", ""));

            StockStatistic stockStatistic = new StockStatistic();
            return stockStatistic.setDate(date).setClosePrice(closePrice).setVolume(volume).
                    setOpenPrice(open).setHighPrice(high).setLowPrice(low);
        } catch (Exception e){
            return null;
        }
    }
}

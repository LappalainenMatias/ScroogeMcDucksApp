package com.example.stockanalyzer.arrayadapter;

import android.app.Activity;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.stock.StockItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class StockArrayAdapter extends ArrayAdapter<StockItem> implements Filterable {

    private List<StockItem> stockItems;
    private List<StockItem> stockItemsCopy;
    private Activity activity;

    public StockArrayAdapter(Context context, Activity activity, List<StockItem> stockItems) {
        super(context, R.layout.item_stock, stockItems);
        this.stockItems = stockItems;
        this.stockItemsCopy = new ArrayList<>(stockItems);
        this.activity = activity;
    }

    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_stock, null, true);
        TextView TVStockName = rowView.findViewById(R.id.TVStockName);
        TextView TVStockDateRange = rowView.findViewById(R.id.TVStockDateRange);//Informs to user the first and last stock statistic date.
        StockItem stockItem = stockItems.get(position);

        TVStockName.setText(stockItem.name);
        if(stockItem.stockStatisticByCalendar.keySet().size() != 0){
            TVStockDateRange.setText("Stock data from " + getStocksDateRange(position));
        } else {
            TVStockDateRange.setText("Stock data was not found");
        }
        return rowView;
    }

    @Override
    public int getCount() {
        return stockItems.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    stockItems = new ArrayList<>(stockItemsCopy);
                } else {
                    ArrayList<StockItem> filteredList = new ArrayList<>();
                    for (StockItem item : stockItemsCopy) {
                        if (item.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    stockItems = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = stockItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                stockItems = (ArrayList<StockItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private String getStocksDateRange(int position) {
        StockItem stockItem = stockItems.get(position);
        GregorianCalendar earliest = null;
        GregorianCalendar latest = null;
        for(GregorianCalendar gregorianCalendar : stockItem.stockStatisticByCalendar.keySet()){
            if(earliest == null || earliest.getTimeInMillis() > gregorianCalendar.getTimeInMillis()){
                earliest = gregorianCalendar;
            }
            if(latest == null || latest.getTimeInMillis() < gregorianCalendar.getTimeInMillis()){
                latest = gregorianCalendar;
            }
        }
        SimpleDateFormat format_dd_MM_yyyy = new SimpleDateFormat("dd-MM-yyyy");
        String formatedEarlier = format_dd_MM_yyyy.format(earliest.getTime());
        String formatedLatest = format_dd_MM_yyyy.format(latest.getTime());
        return formatedEarlier + " to " + formatedLatest;
    }
}

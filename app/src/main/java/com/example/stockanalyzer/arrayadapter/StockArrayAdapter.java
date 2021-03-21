package com.example.stockanalyzer.arrayadapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.core.util.Pair;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.stock.StockItem;
import com.example.stockanalyzer.stock.StockItemAnalyzer;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class StockArrayAdapter extends ArrayAdapter<StockItem> implements Filterable {

    public List<StockItem> stockItems;
    private final List<StockItem> stockItemsCopy;
    private final Activity activity;

    public StockArrayAdapter(Context context, Activity activity, List<StockItem> stockItems) {
        super(context, R.layout.item_stock, stockItems);
        this.stockItems = stockItems;
        this.stockItemsCopy = new ArrayList<>(stockItems);
        this.activity = activity;
    }

    @NotNull
    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_stock, null, true);
        TextView TVStockName = rowView.findViewById(R.id.TVStockName);
        TextView TVStockDateRange = rowView.findViewById(R.id.TVStockDateRange);
        StockItem stockItem = stockItems.get(position);

        TVStockName.setText(stockItem.fileName);
        if (stockItem.stockStatisticByCalendar.keySet().size() != 0) {
            TVStockDateRange.setText("Stock data from " + getStocksDateRange(position));
        } else {
            TVStockDateRange.setText("Stock data was not found!");
        }
        return rowView;
    }

    @Override
    public int getCount() {
        return stockItems.size();
    }

    @NotNull
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
                        if (item.fileName.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    stockItems = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = stockItems;
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                stockItems = (ArrayList<StockItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private String getStocksDateRange(int position) {
        StockItemAnalyzer stockItemAnalyzer = new StockItemAnalyzer(stockItems.get(position));
        Pair<GregorianCalendar, GregorianCalendar> dateRange = stockItemAnalyzer.getStocksDateRange();
        SimpleDateFormat format_dd_MM_yyyy = new SimpleDateFormat("dd/MM/yyyy");
        String formattedEarlier = format_dd_MM_yyyy.format(dateRange.first.getTime());
        String formattedLatest = format_dd_MM_yyyy.format(dateRange.second.getTime());
        return formattedEarlier + " to " + formattedLatest;
    }
}

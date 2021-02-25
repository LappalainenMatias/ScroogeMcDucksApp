package com.example.stockanalyzer.arrayadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.core.util.Pair;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.stock.StockItem;
import com.example.stockanalyzer.stock.StockItemAnalyzer;
import com.example.stockanalyzer.stock.TradingVolumeAndPriceChange;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class TradingVolumePriceChangeArrayAdapter extends ArrayAdapter<TradingVolumeAndPriceChange>{

    private List<TradingVolumeAndPriceChange> tradingVolumeAndPriceChanges;
    private Activity activity;
    private SimpleDateFormat format_dd_MM_yyyy = new SimpleDateFormat("dd-MM-yyyy");

    public TradingVolumePriceChangeArrayAdapter(Context context, Activity activity,
                                                List<TradingVolumeAndPriceChange> tradingVolumeAndPriceChanges) {
        super(context, R.layout.item_trading_volume_and_price_change, tradingVolumeAndPriceChanges);
        this.tradingVolumeAndPriceChanges = tradingVolumeAndPriceChanges;
        this.activity = activity;
    }

    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_trading_volume_and_price_change, null, true);
        TextView TVDate = rowView.findViewById(R.id.TVDate);
        TextView TVTradingVolume = rowView.findViewById(R.id.TVTradingVolume);
        TextView TVPriceChange = rowView.findViewById(R.id.TVPriceChange);
        TradingVolumeAndPriceChange tvpc = tradingVolumeAndPriceChanges.get(position);

        // TODO: 25.2.2021 Strings to R.strings
        TVDate.setText(format_dd_MM_yyyy.format(tvpc.gregorianCalendar.getTime()));
        TVTradingVolume.setText("Trading volume: " + ((int)tvpc.tradingVolume));
        TVPriceChange.setText("Price change: " + String.format("%.3g%n", tvpc.priceChange));

        return rowView;
    }
}

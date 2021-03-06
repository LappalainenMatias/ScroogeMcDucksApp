package com.example.stockanalyzer.arrayadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.stockanalyzer.R;
import com.example.stockanalyzer.stock.TradingVolumeAndPriceChange;
import java.text.SimpleDateFormat;
import java.util.List;

public class TradingVolumePriceChangeArrayAdapter extends ArrayAdapter<TradingVolumeAndPriceChange>{

    private final List<TradingVolumeAndPriceChange> tradingVolumeAndPriceChanges;
    private final Activity activity;
    private final SimpleDateFormat format_dd_MM_yyyy = new SimpleDateFormat("dd/MM/yyyy");

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
        TVPriceChange.setText("Price change: " + round(tvpc.priceChange, 3) + " $");

        return rowView;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}

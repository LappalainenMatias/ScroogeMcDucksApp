package com.example.stockanalyzer.arrayadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.stock.OpeningPriceSMA5;
import com.example.stockanalyzer.stock.TradingVolumeAndPriceChange;

import java.text.SimpleDateFormat;
import java.util.List;

public class OpeningPriceSMA5ArrayAdapter extends ArrayAdapter<OpeningPriceSMA5> {

    private final List<OpeningPriceSMA5> openingPriceSMA5s;
    private final Activity activity;
    private final SimpleDateFormat format_dd_MM_yyyy = new SimpleDateFormat("dd/MM/yyyy");

    public OpeningPriceSMA5ArrayAdapter(Context context, Activity activity,
                                        List<OpeningPriceSMA5> openingPriceSMA5s) {
        super(context, R.layout.item_trading_volume_and_price_change, openingPriceSMA5s);
        this.openingPriceSMA5s = openingPriceSMA5s;
        this.activity = activity;
    }

    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_date_pricechange, null, true);
        TextView TVDate = rowView.findViewById(R.id.TVDate);
        TextView TVPriceChange = rowView.findViewById(R.id.TVPriceChange);
        OpeningPriceSMA5 openingPriceSMA5 = openingPriceSMA5s.get(position);

        // TODO: 25.2.2021 Strings to R.strings
        TVDate.setText(format_dd_MM_yyyy.format(openingPriceSMA5.gregorianCalendar.getTime()));
        TVPriceChange.setText("Price change: " + round(openingPriceSMA5.getOpeningPriceAndSMA5Difference(),2) + " %");

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

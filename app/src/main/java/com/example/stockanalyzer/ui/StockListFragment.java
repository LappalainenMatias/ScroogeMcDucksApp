package com.example.stockanalyzer.ui;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.stock.StockItem;
import com.example.stockanalyzer.viewmodels.StockListViewModel;

import java.util.ArrayList;

public class StockListFragment extends Fragment {

    private StockListViewModel stockListViewModel;

    View viewContainer;

    public static StockListFragment newInstance() {
        return new StockListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.stocklist_fragment, container, false);

        SearchView searchView = viewContainer.findViewById(R.id.action_search);
        TextView textView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        textView.setTextColor(Color.WHITE);
        textView.setHintTextColor(Color.WHITE);
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_search_24px));
        ImageView searchClose = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        searchClose.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_clear_24px));
        return viewContainer;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        stockListViewModel = ViewModelProviders.of(this).get(StockListViewModel.class);
        ListView listView = viewContainer.findViewById(R.id.LVStocks);
        stockListViewModel.getStockItems().observe(getViewLifecycleOwner(), stockItems -> {
            ArrayList<String> stockNames = new ArrayList<>();
            for(StockItem stockItem : stockItems){
                stockNames.add(stockItem.name);
            }
            ArrayAdapter<String> adapt = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, stockNames);
            listView.setAdapter(adapt);
        });
    }

}
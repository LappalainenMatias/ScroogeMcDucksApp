package com.example.stockanalyzer.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.viewmodels.StockListViewModel;

import java.util.ArrayList;

public class StockListFragment extends Fragment {

    private StockListViewModel mViewModel;

    View viewContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.stocklist_fragment, container, false);
        ListView listView = viewContainer.findViewById(R.id.LVStocks);
        ArrayList<String> items = new ArrayList<>();
        items.add("1");
        items.add("2");
        items.add("1");
        items.add("2");
        items.add("1");
        items.add("2");
        items.add("1");
        items.add("2");
        items.add("1");
        items.add("2");
        items.add("1");
        items.add("2");
        items.add("1");
        items.add("2");
        items.add("1");
        items.add("2");
        items.add("1");
        items.add("2");
        items.add("1");
        items.add("2");
        items.add("1");
        items.add("2");
        items.add("1");
        items.add("2");
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapt);
        return viewContainer;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StockListViewModel.class);
    }

}
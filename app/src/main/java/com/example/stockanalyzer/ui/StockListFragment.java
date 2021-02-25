package com.example.stockanalyzer.ui;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.arrayadapter.StockArrayAdapter;
import com.example.stockanalyzer.stock.StockItem;
import com.example.stockanalyzer.viewmodels.StockListViewModel;

import java.util.ArrayList;

public class StockListFragment extends Fragment {

    private StockListViewModel stockListViewModel;

    View viewContainer;
    private ListView LVStocks;
    private StockArrayAdapter stockAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.stocklist_fragment, container, false);
        SearchView searchView = viewContainer.findViewById(R.id.action_search);
        TextView textView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        ImageView searchClose = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);

        searchIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_search_24px));
        textView.setTextColor(Color.WHITE);
        textView.setHintTextColor(Color.WHITE);
        searchClose.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_clear_24px));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (stockAdapter.getFilter() != null) {
                    stockAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        return viewContainer;
    }

    // TODO: 25/02/2021 Replace deprecated code
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        stockListViewModel = ViewModelProviders.of(this).get(StockListViewModel.class);
        LVStocks = viewContainer.findViewById(R.id.LVStocks);

        stockListViewModel.getStockItems().observe(getViewLifecycleOwner(), stockItems -> {
            stockAdapter = new StockArrayAdapter(getContext(), getActivity(), stockItems);
            LVStocks.setAdapter(stockAdapter);
        });

        LVStocks.setOnItemClickListener((parent, view, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("stockId", stockAdapter.stockItems.get(position).id);
            Navigation.findNavController(view).navigate(R.id.action_stockListFragment_to_stockFragment, bundle);
        });
    }

}
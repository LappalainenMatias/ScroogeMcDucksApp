package com.example.stockanalyzer.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.viewmodels.StockListViewModel;

public class StockListFragment extends Fragment {

    private StockListViewModel mViewModel;

    public static StockListFragment newInstance() {
        return new StockListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stocklist_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StockListViewModel.class);
        // TODO: Use the ViewModel
    }

}
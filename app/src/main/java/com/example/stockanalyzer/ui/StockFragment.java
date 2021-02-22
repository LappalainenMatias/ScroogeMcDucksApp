package com.example.stockanalyzer.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.viewmodels.StockListViewModel;

public class StockFragment extends Fragment {

    private StockListViewModel mViewModel;

    public static StockListFragment newInstance() {
        return new StockListFragment();
    }

    View containerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        containerView =  inflater.inflate(R.layout.stock_fragment, container, false);
        return containerView;
    }

}

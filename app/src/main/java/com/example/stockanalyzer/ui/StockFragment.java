package com.example.stockanalyzer.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.viewmodels.StockListViewModel;

public class StockFragment extends Fragment {

    private StockListViewModel mViewModel;

    public static StockFragment newInstance() {
        return new StockFragment();
    }

    View containerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        containerView = inflater.inflate(R.layout.stock_fragment, container, false);
        Spinner spinner = containerView.findViewById(R.id.SpinnerCategories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getCategories());
        adapter.setDropDownViewResource(R.layout.item_spinner);
        spinner.setAdapter(adapter);
        return containerView;
    }

    public String[] getCategories(){
        String[] categories = new String[3];
        categories[0] = getResources().getString(R.string.categories_1);
        categories[1] = getResources().getString(R.string.categories_2);
        categories[2] = getResources().getString(R.string.categories_3);
        return categories;
    }

}

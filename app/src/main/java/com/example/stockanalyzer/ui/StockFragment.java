package com.example.stockanalyzer.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.viewmodels.StockViewModel;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class StockFragment extends Fragment {

    private StockViewModel stockViewModel;

    private DatePickerDialog.OnDateSetListener DatePickerRangeEnd;
    private DatePickerDialog.OnDateSetListener DatePickerRangeStart;
    private TextInputEditText EDDateRangeStart;
    private TextInputEditText EDDateRangeEnd;
    private TextInputLayout textInputLayout;
    private TextView TVAnswer;
    private Toolbar toolbar;
    View containerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        stockViewModel = new StockViewModel(getArguments().getInt("stockId", -1));

        containerView = inflater.inflate(R.layout.stock_fragment, container, false);
        textInputLayout = containerView.findViewById(R.id.textInputLayout);
        EDDateRangeStart = containerView.findViewById(R.id.EDDateRangeStart);
        EDDateRangeEnd = containerView.findViewById(R.id.EDDateRangeEnd);
        TVAnswer = containerView.findViewById(R.id.TVAnswer);
        toolbar = containerView.findViewById(R.id.StockToolbar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getCategories());
        ((MaterialAutoCompleteTextView) textInputLayout.getEditText()).setAdapter(adapter);
        ((MaterialAutoCompleteTextView) textInputLayout.getEditText()).setText(adapter.getItem(1), false);

        createObservers();
        createViewListeners();
        return containerView;
    }

    private void createObservers() {
        stockViewModel.getStockName().observe(getViewLifecycleOwner(), stockName -> {
            toolbar.setTitle(stockName);
        });

        stockViewModel.getDateRange().observe(getViewLifecycleOwner(), dateRange -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            EDDateRangeStart.setText(sdf.format(dateRange.first.getTime()));
            EDDateRangeEnd.setText(sdf.format(dateRange.second.getTime()));
            stockViewModel.analyze();
        });

        stockViewModel.getSelectedCategory().observe(getViewLifecycleOwner(), category -> {
            stockViewModel.analyze();
        });

        stockViewModel.getAnswer().observe(getViewLifecycleOwner(), answer -> {
            if(answer.length() == 0){
                TVAnswer.setVisibility(View.GONE);
            } else {
                TVAnswer.setVisibility(View.VISIBLE);
            }
            TVAnswer.setText(Html.fromHtml(answer));
        });
    }


    private void createViewListeners() {
        DatePickerRangeStart = (view, year, monthOfYear, dayOfMonth) -> {
            GregorianCalendar rangeStart = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            GregorianCalendar rangeEnd = stockViewModel.getDateRange().getValue().second;
            stockViewModel.getDateRange().setValue(new Pair<>(rangeStart, rangeEnd));
        };

        DatePickerRangeEnd = (view, year, monthOfYear, dayOfMonth) -> {
            GregorianCalendar rangeStart = stockViewModel.getDateRange().getValue().first;
            GregorianCalendar rangeEnd = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            stockViewModel.getDateRange().setValue(new Pair<>(rangeStart, rangeEnd));
        };

        EDDateRangeStart.setOnClickListener(v -> new DatePickerDialog(getActivity(),
                DatePickerRangeStart,
                stockViewModel.getDateRange().getValue().first.get(Calendar.YEAR),
                stockViewModel.getDateRange().getValue().first.get(Calendar.MONTH),
                stockViewModel.getDateRange().getValue().first.get(Calendar.DAY_OF_MONTH)).show());

        EDDateRangeEnd.setOnClickListener(v -> new DatePickerDialog(getActivity(),
                DatePickerRangeEnd,
                stockViewModel.getDateRange().getValue().second.get(Calendar.YEAR),
                stockViewModel.getDateRange().getValue().second.get(Calendar.MONTH),
                stockViewModel.getDateRange().getValue().second.get(Calendar.DAY_OF_MONTH)).show());

        toolbar.setNavigationOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_stockFragment_to_stockListFragment);
        });

        ((MaterialAutoCompleteTextView) textInputLayout.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
            }
        });
    }

    public String[] getCategories() {
        String[] categories = new String[3];
        categories[0] = getResources().getString(R.string.categories_1);
        categories[1] = getResources().getString(R.string.categories_2);
        categories[2] = getResources().getString(R.string.categories_3);
        return categories;
    }

}

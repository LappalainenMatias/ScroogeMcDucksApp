package com.example.stockanalyzer.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.viewmodels.StockListViewModel;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StockFragment extends Fragment {

    private StockListViewModel mViewModel;
    private TextInputEditText EDDateRangeStart;
    private TextInputEditText EDDateRangeEnd;
    private Calendar rangeStartCalendar;
    private Calendar rangeEndCalendar;

    public static StockFragment newInstance() {
        return new StockFragment();
    }

    View containerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        containerView = inflater.inflate(R.layout.stock_fragment, container, false);
        TextInputLayout textInputLayout = containerView.findViewById(R.id.textInputLayout);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getCategories());
        ((MaterialAutoCompleteTextView) textInputLayout.getEditText()).setAdapter(adapter);
        ((MaterialAutoCompleteTextView) textInputLayout.getEditText()).setText(adapter.getItem(1), false);
        EDDateRangeStart = containerView.findViewById(R.id.EDDateRangeStart);
        EDDateRangeEnd = containerView.findViewById(R.id.EDDateRangeEnd);
        rangeStartCalendar = Calendar.getInstance();
        rangeEndCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener rangeStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                rangeStartCalendar.set(Calendar.YEAR, year);
                rangeStartCalendar.set(Calendar.MONTH, monthOfYear);
                rangeStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateRangeStartLabel();
            }
        };

        final DatePickerDialog.OnDateSetListener rangeEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                rangeEndCalendar.set(Calendar.YEAR, year);
                rangeEndCalendar.set(Calendar.MONTH, monthOfYear);
                rangeEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateRangeEndLabel();
            }
        };

        EDDateRangeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), rangeStart, rangeStartCalendar
                        .get(Calendar.YEAR), rangeStartCalendar.get(Calendar.MONTH),
                        rangeStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        EDDateRangeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), rangeEnd, rangeEndCalendar
                        .get(Calendar.YEAR), rangeEndCalendar.get(Calendar.MONTH),
                        rangeEndCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return containerView;
    }

    private void updateRangeStartLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        EDDateRangeStart.setText(sdf.format(rangeStartCalendar.getTime()));
    }
    private void updateRangeEndLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        EDDateRangeEnd.setText(sdf.format(rangeEndCalendar.getTime()));
    }


    public String[] getCategories() {
        String[] categories = new String[3];
        categories[0] = getResources().getString(R.string.categories_1);
        categories[1] = getResources().getString(R.string.categories_2);
        categories[2] = getResources().getString(R.string.categories_3);
        return categories;
    }

}

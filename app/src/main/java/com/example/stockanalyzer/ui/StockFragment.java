package com.example.stockanalyzer.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.arrayadapter.OpeningPriceSMA5ArrayAdapter;
import com.example.stockanalyzer.arrayadapter.TradingVolumePriceChangeArrayAdapter;
import com.example.stockanalyzer.viewmodels.StockListViewModel;
import com.example.stockanalyzer.viewmodels.StockViewModel;
import com.example.stockanalyzer.viewmodels.ViewModelFactory;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StockFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;

    private StockViewModel stockViewModel;

    private DatePickerDialog.OnDateSetListener DatePickerRangeEnd;
    private DatePickerDialog.OnDateSetListener DatePickerRangeStart;
    private TextInputEditText EDDateRangeStart;
    private TextInputEditText EDDateRangeEnd;
    private TextInputLayout textInputLayout;
    private AutoCompleteTextView TVCategory;
    private TextView TVAnswer;
    private Toolbar toolbar;
    private ListView LVAnswer;
    View containerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null && stockViewModel == null) {
            viewModelFactory.setFileName(getArguments()
                    .getString("stockFileName", ""));
            stockViewModel = new ViewModelProvider(this, viewModelFactory)
                    .get(StockViewModel.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        containerView = inflater.inflate(R.layout.stock_fragment, container, false);
        textInputLayout = containerView.findViewById(R.id.textInputLayout);
        TVCategory = containerView.findViewById(R.id.category);
        EDDateRangeStart = containerView.findViewById(R.id.EDDateRangeStart);
        EDDateRangeEnd = containerView.findViewById(R.id.EDDateRangeEnd);
        TVAnswer = containerView.findViewById(R.id.TVAnswer);
        LVAnswer = containerView.findViewById(R.id.LLAnswer);
        toolbar = containerView.findViewById(R.id.StockToolbar);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                stockViewModel.getCategories().getValue());
        ((MaterialAutoCompleteTextView) textInputLayout.getEditText())
                .setAdapter(adapter);
        ((MaterialAutoCompleteTextView) textInputLayout.getEditText())
                .setText(adapter.getItem(0), false);

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

        //Toolbar menu needs this to work
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        ((MaterialAutoCompleteTextView) textInputLayout.getEditText()).setOnItemClickListener(
                (parent, view, position, id) -> stockViewModel.getSelectedCategory()
                        .setValue(stockViewModel.getCategories().getValue().get(position)));
        return containerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stockViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, categories);
            ((MaterialAutoCompleteTextView) textInputLayout.getEditText())
                    .setAdapter(adapter);
            ((MaterialAutoCompleteTextView) textInputLayout.getEditText())
                    .setText(stockViewModel.getSelectedCategory().getValue(), false);
        });

        stockViewModel.getStockFileName().observe(getViewLifecycleOwner(), stockName -> {
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
            if (answer.length() == 0) {
                TVAnswer.setVisibility(View.GONE);
            } else {
                TVAnswer.setVisibility(View.VISIBLE);
            }
            TVAnswer.setText(Html.fromHtml(answer));
        });

        stockViewModel.getTradingVolumesAndPriceChanges().observe(getViewLifecycleOwner(),
                tradingVolumeAndPriceChanges -> {
                    if (tradingVolumeAndPriceChanges != null) {
                        ArrayAdapter adapter = new TradingVolumePriceChangeArrayAdapter(
                                getContext(), getActivity(), tradingVolumeAndPriceChanges);
                        LVAnswer.setAdapter(adapter);
                    }
                });

        stockViewModel.getOpeningPriceSMA5s().observe(getViewLifecycleOwner(),
                openingPriceSMA5s -> {
                    if (openingPriceSMA5s != null) {
                        ArrayAdapter adapter = new OpeningPriceSMA5ArrayAdapter(
                                getContext(), getActivity(), openingPriceSMA5s);
                        LVAnswer.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_stock, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.delete_file) {
            MaterialAlertDialogBuilder dialogBuilder = createAlertDialogBuilder();
            dialogBuilder.show();
            return true;
        } else if (menuItem.getItemId() == android.R.id.home) {
            Navigation.findNavController(containerView).navigateUp();
            return true;
        }
        return false;
    }

    private MaterialAlertDialogBuilder createAlertDialogBuilder() {
        return new MaterialAlertDialogBuilder(getContext())
                .setTitle("Do you want to delete " + stockViewModel.getStockFileName().getValue() + "?")
                .setPositiveButton("Delete", (dialogInterface, i) -> {
                    boolean fileDeleted = stockViewModel.deleteFile();
                    if (fileDeleted) {
                        Toast.makeText(getContext(), "File deleted!", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(containerView).navigateUp();
                    } else {
                        Toast.makeText(getContext(), "Failed to delete the file!", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("CANCEL", (dialogInterface, i) -> {
                });
    }
}

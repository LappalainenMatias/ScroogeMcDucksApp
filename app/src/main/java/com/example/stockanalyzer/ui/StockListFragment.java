package com.example.stockanalyzer.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.arrayadapter.StockArrayAdapter;
import com.example.stockanalyzer.viewmodels.StockListViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class StockListFragment extends Fragment {

    View viewContainer;
    private Button BTNAddFile;
    private MaterialButton BTNChooseFile;
    private TextInputLayout inputStockName;
    private ListView LVStocks;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;
    private AlertDialog AlertDialogStockData;

    private StockListViewModel stockListViewModel;
    private StockArrayAdapter stockAdapter;
    private ActivityResultLauncher<Intent> resultLauncher;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.stocklist_fragment, container, false);

        // TODO: 10.3.2021 Refactor. Rename views
        SearchView searchView = viewContainer.findViewById(R.id.action_search);
        LVStocks = viewContainer.findViewById(R.id.LVStocks);
        FloatingActionButton FABAdd = viewContainer.findViewById(R.id.FABAdd);
        TextView textView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        ImageView searchClose = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);

        searchIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_search_24px));
        textView.setTextColor(Color.WHITE);
        textView.setHintTextColor(Color.WHITE);
        searchClose.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_clear_24px));

        FABAdd.setOnClickListener(v -> {
            materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Choose a file (.csv) which contains stock data")
                    .setView(getAttachFileLayout())
                    .setPositiveButton("ADD", (dialogInterface, i) -> {
                        if (stockListViewModel.saveStockDataFile(inputStockName.getEditText().getText().toString())) {
                            Toast.makeText(getContext(), "Successfully saved stock data!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Failed to save stock data!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("CANCEL", (dialogInterface, i) -> {
                    });
            AlertDialogStockData = materialAlertDialogBuilder.show();
            BTNAddFile = AlertDialogStockData.getButton(DialogInterface.BUTTON_POSITIVE);
            stockListViewModel.getSelectedFileUri().setValue(null);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                stockAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return viewContainer;
    }

    // TODO: 13.3.2021 Rotation cases exception

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stockListViewModel = new ViewModelProvider(this).get(StockListViewModel.class);
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(Uri.parse("/downloads"), "text/csv");
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Toast.makeText(getContext(), "File selected!", Toast.LENGTH_SHORT).show();
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        stockListViewModel.getSelectedFileUri().setValue(uri);
                    } else {
                        Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
        stockListViewModel.getStockItems().observe(getViewLifecycleOwner(), stockItems -> {
            stockAdapter = new StockArrayAdapter(getContext(), getActivity(), stockItems);
            LVStocks.setAdapter(stockAdapter);
        });

        stockListViewModel.getSelectedFileUri().observe(getViewLifecycleOwner(), selectedFileUri -> {
            // TODO: 12.3.2021 Refactor
            if (BTNChooseFile == null || inputStockName == null || BTNAddFile == null) {
                return;
            }
            if (selectedFileUri == null) {
                BTNChooseFile.setVisibility(View.VISIBLE);
                inputStockName.setVisibility(View.GONE);
                BTNAddFile.setAlpha(0.5f);
                BTNAddFile.setClickable(false);
            } else {
                AlertDialogStockData.setTitle("Add a name for the stock data");
                BTNChooseFile.setVisibility(View.GONE);
                inputStockName.setVisibility(View.VISIBLE);
                inputStockName.requestFocus();

                inputStockName.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.length() > 0) {
                            BTNAddFile.setAlpha(1f);
                            BTNAddFile.setClickable(true);
                        } else {
                            BTNAddFile.setAlpha(0.5f);
                            BTNAddFile.setClickable(false);
                        }
                    }
                });
            }
        });

        LVStocks.setOnItemClickListener((parent, v, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putString("stockFileName", stockAdapter.stockItems.get(position).fileName);
            Navigation.findNavController(v).navigate(R.id.action_stockListFragment_to_stockFragment, bundle);
        });
    }

    private LinearLayout getAttachFileLayout() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.choose_file_btn, null, true);
        BTNChooseFile = linearLayout.findViewById(R.id.BTNChooseFile);
        inputStockName = linearLayout.findViewById(R.id.LayoutStocksName);
        BTNChooseFile.setOnClickListener(v -> {
            resultLauncher.launch(intent);
        });
        return linearLayout;
    }
}
package com.example.stockanalyzer.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockanalyzer.R;
import com.example.stockanalyzer.arrayadapter.StockArrayAdapter;
import com.example.stockanalyzer.stock.StockItem;
import com.example.stockanalyzer.viewmodels.StockListViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StockListFragment extends Fragment {

    private StockListViewModel stockListViewModel;

    View viewContainer;
    private MaterialButton materialButton;
    private ListView LVStocks;
    private StockArrayAdapter stockAdapter;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewContainer = inflater.inflate(R.layout.stocklist_fragment, container, false);
        SearchView searchView = viewContainer.findViewById(R.id.action_search);
        TextView textView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        ImageView searchClose = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        FloatingActionButton FABAdd = viewContainer.findViewById(R.id.FABAdd);

        searchIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_search_24px));
        textView.setTextColor(Color.WHITE);
        textView.setHintTextColor(Color.WHITE);
        searchClose.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_clear_24px));

        FABAdd.setOnClickListener(v -> {
            materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Add stock data")
                    .setMessage("Application only supports csv files.")
                    .setView(getMaterialButton())
                    .setPositiveButton("ADD", (dialogInterface, i) -> {
                    })
                    .setNegativeButton("CANCEL", (dialogInterface, i) -> {
                    });

            AlertDialog alertDialog = materialAlertDialogBuilder.show();
            Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            button.setAlpha(0.5f);
            button.setClickable(false);
        });

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

    private ActivityResultLauncher<Intent> resultLauncher;
    private Intent intent;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse("/downloads"); // a directory
        intent.setDataAndType(uri, "*/*");
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        materialButton.setText(data.getPackage());
                    }
                });
    }

    private LinearLayout getMaterialButton() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.choose_file_btn, null, true);
        materialButton = linearLayout.findViewById(R.id.BTNChooseFile);
        materialButton.setOnClickListener(v -> {
            resultLauncher.launch(intent);
        });

        return linearLayout;
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
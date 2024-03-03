package com.mhndroid.quizapp.View.UI;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.mhndroid.quizapp.R;
import com.mhndroid.quizapp.Service.Model.CategoriesModel;
import com.mhndroid.quizapp.Service.Model.QuestionModel;
import com.mhndroid.quizapp.View.Adapter.CategoriesAdapter;
import com.mhndroid.quizapp.ViewModel.CategoriesViewModel;

import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    // ViewModel for categories data
    CategoriesViewModel viewModel;

    // Adapter for categories data
    CategoriesAdapter adapter;

    // UI components
    private ProgressBar progressBar;
    RecyclerView recyclerView;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        // Initializing ViewModel for handling categories data.
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                  .getInstance(getActivity(this).getApplication())).get(CategoriesViewModel.class);


        // Initializing UI components.
        recyclerView = findViewById(R.id.categoriesRecyclerViewId);
        progressBar = findViewById(R.id.quizListProgressbar);


        //Set layout for the recycleView and set adapter in recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CategoriesAdapter();
        recyclerView.setAdapter(adapter);


        // Load categories.
        viewModel.getMutableLiveData().observe(this, new Observer<List<CategoriesModel>>() {
            @Override
            public void onChanged(List<CategoriesModel> categoriesModels) {
                progressBar.setVisibility(View.GONE);
                adapter.setAll_quiz_categories(categoriesModels);
                adapter.notifyDataSetChanged();
            }
        });


    }


}
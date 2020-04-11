package com.bhavaniprasad.tmdbapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhavaniprasad.tmdbapp.Adapter.TDMBListAdapter;
import com.bhavaniprasad.tmdbapp.Model.ListModel;
import com.bhavaniprasad.tmdbapp.ViewModel.ListViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListViewModel listViewModel;
    private ArrayList<ListModel> arrayList;
    private ProgressBar progressBar;
    private Button btn_retry;
    private RelativeLayout layout_nonetwork;
    NetworkConnection object;
    private TextView title;
    private TDMBListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        listViewModel= ViewModelProviders.of(this).get(ListViewModel.class);
        progressBar = findViewById(R.id.progress_bar);
        btn_retry = findViewById(R.id.retry);
        layout_nonetwork = findViewById(R.id.no_network);
        object = new NetworkConnection();

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.light_gray));

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E8E8E8")));
            getSupportActionBar().setCustomView(R.layout.action_bar);
            getSupportActionBar().setElevation(0);
            title=findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
            title.setText("TDMBApp");
            title.measure(0,0);

        }

        if(!object.isConnected(this)){
            layout_nonetwork.setVisibility(View.VISIBLE);
        }
        else{
            layout_nonetwork.setVisibility(View.INVISIBLE);
            getdata();
        }

        listViewModel.getShowProgressBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        listViewModel.getShow_networkError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.INVISIBLE);
                    layout_nonetwork.setVisibility(View.VISIBLE);
                } else {
                    layout_nonetwork.setVisibility(View.GONE);
                }
            }
        });


        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!object.isConnected(MainActivity.this)){
                    progressBar.setVisibility(View.INVISIBLE);
                    layout_nonetwork.setVisibility(View.VISIBLE);
                }
                else{
                    layout_nonetwork.setVisibility(View.INVISIBLE);
                    getdata();
                }
            }
        });


    }

    public void getdata(){

        listViewModel.getMutableLiveData(MainActivity.this).observe(this, new Observer<ArrayList<ListModel>>() {
            @Override
            public void onChanged(ArrayList<ListModel> listModels) {
                if(listModels.size()>0){
                    arrayList=(ArrayList<ListModel>) listModels.get(0).getResults();
                    listAdapter = new TDMBListAdapter(MainActivity.this,arrayList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(listAdapter);
                }
            }
        });
    }
}

package com.example.lostandfound;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerviewActivity extends AppCompatActivity {

    MyDataViewModel mMyDataViewModel;
    RecyclerView recyclerView;
    MyDataListAdapter myDataListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        mMyDataViewModel = new ViewModelProvider(this).get(MyDataViewModel.class);
        myDataListAdapter = new MyDataListAdapter(new MyDataListAdapter.MyDataDiff(), this, mMyDataViewModel);
        mMyDataViewModel.getAllMyData().observe(this, myData->{
            myDataListAdapter.submitList(myData);
        });

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(myDataListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

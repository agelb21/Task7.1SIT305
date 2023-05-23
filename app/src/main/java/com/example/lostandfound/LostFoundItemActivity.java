package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class LostFoundItemActivity extends AppCompatActivity {

    private MyDataViewModel mMyDataViewModel;
    Button buttonRemove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

         mMyDataViewModel = new ViewModelProvider(this).get(MyDataViewModel.class);

         Bundle extras = getIntent().getExtras();

         //Item details
         String listing = extras.getString("listItem");
         TextView itemDetails = (TextView)findViewById(R.id.textViewItemDetails);
         itemDetails.setText(listing);

         int itemID = extras.getInt("intID");

        buttonRemove = findViewById(R.id.buttonRemove);
        buttonRemove.setOnClickListener(view -> {
            MyDataViewModel.deleteByID(itemID);

            Intent intent = new Intent(LostFoundItemActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
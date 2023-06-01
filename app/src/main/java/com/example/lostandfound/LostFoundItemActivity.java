package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LostFoundItemActivity extends AppCompatActivity {
    Button buttonRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        //intent with extras passed in list adapter when user selects a recycler view
        //or passed from mapActivity when user selects marker
        Bundle extras = getIntent().getExtras();

        //Item details
        String listing = extras.getString("listItem");
        TextView itemDetails = (TextView)findViewById(R.id.textViewItemDetails);
        itemDetails.setText(listing);

        int itemID = extras.getInt("intID");

        //remove item from list and db
        buttonRemove = findViewById(R.id.buttonRemove);
        buttonRemove.setOnClickListener(view -> {
            MyDataViewModel.deleteByID(itemID);

            Intent intent = new Intent(LostFoundItemActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
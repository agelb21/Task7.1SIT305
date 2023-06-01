package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.google.android.libraries.places.api.Places;

public class MainActivity extends AppCompatActivity {
    private MyDataViewModel mMyDataViewModel;
    public static final int NEW_AD_ACTIVITY_REQUEST_CODE = 1;
    public static final int RECYCLERVIEW_ACTIVITY_REQUEST_CODE = 1;
    public static final int MAPS_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Places
        //Initialize SDK
        //Could improve using secrets for API Key
        Places.initialize(getApplicationContext(), "API KEY");

        mMyDataViewModel = new ViewModelProvider(this).get(MyDataViewModel.class);

        Button buttonCreateAd = findViewById(R.id.buttonCreateAd);
        buttonCreateAd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewAdActivity.class);
            startActivityForResult(intent, NEW_AD_ACTIVITY_REQUEST_CODE);

        });

        Button buttonShowAds = findViewById(R.id.buttonShowAds);
        buttonShowAds.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RecyclerviewActivity.class);
            startActivityForResult(intent, RECYCLERVIEW_ACTIVITY_REQUEST_CODE);
        });

        Button buttonShowOnMap = findViewById(R.id.buttonShowOnMap);
        buttonShowOnMap.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivityForResult(intent, MAPS_ACTIVITY_REQUEST_CODE);
        });
    }

    //Result from user saving data in NewAdActivity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_AD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            String item =  data.getStringExtra("item");
            String phone =  data.getStringExtra("phone");
            String description =  data.getStringExtra("description");
            String itemDate =  data.getStringExtra("itemDate");
            String location =  data.getStringExtra("location");
            String postType =  data.getStringExtra("postType");
            Double itemLat = Double.parseDouble(data.getStringExtra("itemLat"));
            Double itemLng = Double.parseDouble(data.getStringExtra("itemLng"));

            MyData myData = new MyData(postType, item, phone, description, itemDate, location, itemLat, itemLng);
            mMyDataViewModel.insert(myData);
        }
    }
}
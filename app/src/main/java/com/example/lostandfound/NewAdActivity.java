package com.example.lostandfound;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class NewAdActivity extends AppCompatActivity {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    // private MyDataViewModel mMyDataViewModel;
    private PlacesClient placesClient;
    private FusedLocationProviderClient fusedLocationClient;

    private RadioButton mRadioButtonFound;
    private EditText mEditItemView, mEditItemPhone, mEditItemDescription, mEditItemDate, mEditItemLocation;
    private Double mItemLat;
    private Double mItemLng;
    private String mPostType;

    //Radio Buttons is Lost or Found checked
    public String getPostType(boolean isChecked) {

        if (isChecked) {
            return "Found";
        } else
            return "Lost";

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

//
//        mMyDataViewModel = new ViewModelProvider(this).get(MyDataViewModel.class);
//        mMyDataViewModel.getAllMyData().observe(this, myData -> {
//
//        });

        placesClient = Places.createClient(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //NewAd Form
        mRadioButtonFound = findViewById(R.id.radioButtonFound);
        mEditItemView = findViewById(R.id.editText_item);
        mEditItemPhone = findViewById(R.id.editText_phone);
        mEditItemDescription = findViewById(R.id.editText_description);
        mEditItemDate = findViewById(R.id.editText_date);
        mEditItemLocation = findViewById(R.id.editText_location);

        //location edit text opens autocomplete location overlay
        mEditItemLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY,
                        Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
                        .setLocationBias(RectangularBounds.newInstance(
                                new LatLng(-37.84725613268904, 145.11486203639666),
                                new LatLng(-37.84446125072331, 145.11225116963115)))
                        .setCountries(Arrays.asList("AU"))
                        .build(getApplicationContext());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

            }
        });

        //get current location button
        Button getLocationButton = findViewById(R.id.button_location);
        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
                getCurrentPlace();
            }
        });


        //save button sends data to main activity to write to db
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditItemView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                boolean isChecked = mRadioButtonFound.isChecked();
                mPostType = getPostType(isChecked);

                replyIntent.putExtra("item", mEditItemView.getText().toString());
                replyIntent.putExtra("phone", mEditItemPhone.getText().toString());
                replyIntent.putExtra("description", mEditItemDescription.getText().toString());
                replyIntent.putExtra("itemDate", mEditItemDate.getText().toString());
                replyIntent.putExtra("location", mEditItemLocation.getText().toString());
                replyIntent.putExtra("postType", mPostType);
                replyIntent.putExtra("itemLat", mItemLat.toString());
                replyIntent.putExtra("itemLng", mItemLng.toString());

                setResult(RESULT_OK, replyIntent);

            }
            finish();
        });


    }

    //get current location name
    private void getCurrentPlace() {
        // Use fields to define the data types to return.
        List<Place.Field> placeFields = Collections.singletonList(Place.Field.NAME);

        // Use the builder to create a FindCurrentPlaceRequest.
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

    // Call findCurrentPlace and handle the response (first check that the user has granted permission).
       if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
            placeResponse.addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    FindCurrentPlaceResponse response = task.getResult();
                    for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                        Log.i(TAG, String.format("Place '%s' has likelihood: %f",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));

                        mEditItemLocation.setText(placeLikelihood.getPlace().getName());
                    }
                } else {
                    Exception exception = task.getException();
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                    }
                }
            });
        } else {
           ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                   REQUEST_LOCATION_PERMISSION);
        }
    }


    //Auto complete location, updates edit text and location
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());

                mEditItemLocation.setText(place.getName());
                mItemLat = place.getLatLng().latitude;
                mItemLng = place.getLatLng().longitude;

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                //user canceled the operation
            }
        }
    }


    //Get Current Location updates latLng
    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
                                mItemLat = latlng.latitude;
                                mItemLng = latlng.longitude;
                            }
                        }
                    });
        }
    }

    //Permission for Current Location
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_LOCATION_PERMISSION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }

}
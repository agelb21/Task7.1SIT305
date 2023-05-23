package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


public class NewAdActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
   // public static final int NEW_AD_ACTIVITY_REQUEST_CODE = 1;

    private MyDataViewModel mMyDataViewModel;
    private EditText mEditItemView, mEditItemPhone, mEditItemDescription, mEditItemDate, mEditItemLocation;
    private RadioButton mRadioButtonFound;

    private String mPostType;

    public String getPostType(boolean isChecked){

        //boolean isChecked = mRadioButtonFound.isChecked();

        if(isChecked){
            return "Found";
        }else
            return "Lost";

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        mRadioButtonFound = findViewById(R.id.radioButtonFound);

        mMyDataViewModel = new ViewModelProvider(this).get(MyDataViewModel.class);

        mMyDataViewModel.getAllMyData().observe(this, myData->{

        });

        mEditItemView = findViewById(R.id.editText_item);
        mEditItemPhone = findViewById(R.id.editText_phone);
        mEditItemDescription = findViewById(R.id.editText_description);
        mEditItemDate = findViewById(R.id.editText_date);
        mEditItemLocation = findViewById(R.id.editText_location);


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

                setResult(RESULT_OK, replyIntent);

            }
            finish();
        });


    }
}
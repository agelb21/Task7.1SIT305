package com.example.lostandfound;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyDataViewModel extends AndroidViewModel {
    private static MyDataRepository mRepository;
    private final LiveData<List<MyData>> mAllMyData;

    public MyDataViewModel (@NonNull Application application){
        super(application);
        mRepository = new MyDataRepository(application);
        mAllMyData = mRepository.getAllMyData();
    }

    public static void deleteByID(int ID) { mRepository.deleteByID(ID);}

    public LiveData<List<MyData>> getAllMyData() {return mAllMyData;}

    public void insert(MyData myData){mRepository.insert(myData);}

    public static void delete(MyData myData){
        mRepository.delete(myData);
    }


}

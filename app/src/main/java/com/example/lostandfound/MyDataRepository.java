package com.example.lostandfound;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MyDataRepository {

    private MyDataDao mMyDataDao;
    private LiveData<List<MyData>> mAllMyData;

    public MyDataRepository(Application application) {

        MyDataRoomDatabase db = MyDataRoomDatabase.getDatabase(application);
        mMyDataDao = db.myDataDao();
        mAllMyData = mMyDataDao.getAllData();
    }

    LiveData<List<MyData>> getAllMyData() {
        return mAllMyData;
    }

    void insert(MyData myData){
        MyDataRoomDatabase.databaseWriteExecutor.execute(()->{
            mMyDataDao.insert(myData);
        });
    }

    public void delete (MyData myData){
        MyDataRoomDatabase.databaseWriteExecutor.execute(()->{
            mMyDataDao.delete(myData);
        });
    }


    public void deleteByID(int id) {
        MyDataRoomDatabase.databaseWriteExecutor.execute(()->{
            mMyDataDao.deleteById(id);
        });
    }

    public List<MyData> getAllData() {
        return mMyDataDao.getAllData().getValue();
    }
}

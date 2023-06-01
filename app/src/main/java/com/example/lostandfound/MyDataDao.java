package com.example.lostandfound;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface  MyDataDao {


    @Insert
    void insert(MyData myData);


    @Delete
    void delete(MyData myData);


    @Query("DELETE FROM myTable")
    void deleteAll();

    @Query("SELECT * FROM mytable")
    LiveData<List<MyData>> getAllData();

    @Query("DELETE FROM myTable WHERE id = :id")
    void deleteById(int id);
}

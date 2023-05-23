package com.example.lostandfound;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Database(entities = {MyData.class}, version = 2, exportSchema = false)
@Database(entities = {MyData.class}, version = 2)
public abstract class MyDataRoomDatabase extends RoomDatabase {

    public abstract MyDataDao myDataDao();
    private static volatile MyDataRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static MyDataRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDataRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDataRoomDatabase.class, "MyData_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}

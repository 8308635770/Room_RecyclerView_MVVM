package com.example.room_viewmodel_livedata_recyclerviewmvvm.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class},version = 1)
public abstract class DataBase extends RoomDatabase {

    public static synchronized DataBase getNotedatabase(Context context) {

        DataBase dataBase = Room.databaseBuilder(context.getApplicationContext(), DataBase.class, "note_database")
                .fallbackToDestructiveMigration()
//                    .addCallback(callback)
                .build();

        return dataBase;

    }
}

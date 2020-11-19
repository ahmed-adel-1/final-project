package com.example.retail.Room;

import android.content.Context;

import androidx.room.Room;

public class RoomFactory {

    private static ProductDatabase database;

    public static ProductDatabase getRoomDatabase(Context context){

        if (database == null){
            database = Room.databaseBuilder(context,ProductDatabase.class,"products_database")
                     .build();
        }

        return database;
    }
}

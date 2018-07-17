package com.ab.searchgooglemapsapi.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class SingletonInstance {
    private static LocationDatabase locationDatabase;

    public static LocationDatabase getDbInstance(Context context){
        if (locationDatabase == null) {
            locationDatabase = Room.databaseBuilder(context,
                    LocationDatabase.class, "location-db").build();
        }
        return locationDatabase;
    }
}

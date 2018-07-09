package com.ab.searchgooglemapsapi.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Location.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}

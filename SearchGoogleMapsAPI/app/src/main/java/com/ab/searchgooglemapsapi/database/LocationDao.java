package com.ab.searchgooglemapsapi.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM Location")
    List<Location> getAll();

    @Query("SELECT * FROM Location WHERE location_id = :locationId")
    Location getByLocationId(String locationId);

    @Insert
    void insert(Location... location);

    @Delete
    void delete(Location location);

}

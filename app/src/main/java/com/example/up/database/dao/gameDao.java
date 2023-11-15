package com.example.up.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.up.database.entities.game;

import java.util.List;

@Dao
public interface gameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(game games);
    @Update
    void update(game games);
    @Delete
    void delete(game games);

    @Query("SELECT * FROM game")
    List<game> getAllGames();
}

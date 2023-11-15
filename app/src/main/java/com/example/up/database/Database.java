package com.example.up.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.up.database.entities.game;
import com.example.up.database.dao.gameDao;

@androidx.room.Database(entities = {game.class}, version = 2)
public abstract class Database extends RoomDatabase {
    public abstract gameDao gameDao();

    public static volatile Database INSTANCE;
    public static Database getDatabase(Context context){
        if (INSTANCE == null){
            synchronized (Database.class){
                if (INSTANCE ==null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            Database.class,
                            "test_database").build();
                }
            }
        }
        return INSTANCE;
    }
}

package com.example.up.database.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.up.database.Database;
import com.example.up.database.entities.game;

import java.util.List;

public class gameViewModel extends AndroidViewModel {
    private Database database;
    public List<game> games;
    public gameViewModel(@NonNull Application application){
        super(application);
        database = Database.getDatabase(getApplication());
    }

    public void addGame(game item){
        Runnable addGames = ()->{
            database.gameDao().insert(item);
        };
        Thread thread = new Thread(addGames);
        thread.start();
    }

    public void deleteGame(game item){
        Runnable deleteGames = ()->{
            database.gameDao().delete(item);
        };
        Thread thread = new Thread(deleteGames);
        thread.start();
    }

    public void updateGame(game item){
        Runnable updateGame = ()->{
            database.gameDao().update(item);
        };
        Thread thread = new Thread(updateGame);
        thread.start();
    }
}

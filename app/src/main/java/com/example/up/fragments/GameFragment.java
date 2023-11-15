package com.example.up.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.up.R;
import com.example.up.adapters.gameAdapter;
import com.example.up.database.entities.game;
import com.example.up.database.Database;
import com.example.up.database.viewmodels.gameViewModel;
import com.example.up.databinding.FragmentGameBinding;

import java.util.List;

public class GameFragment extends Fragment {

    FragmentGameBinding binding;
    gameViewModel viewModel;
    gameAdapter gameAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        viewModel = new ViewModelProvider(this).get(gameViewModel.class);
        binding = FragmentGameBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        showGameList();
        addBtnInit();
        deleteGame();
        updateGame();
    }

    private void showGameList(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Database db = Database.getDatabase(getContext());
                List<game> artistsList = db.gameDao().getAllGames();
                gameAdapter = new gameAdapter(getContext(), R.layout.game_item, artistsList);
                binding.gamesView.setAdapter(gameAdapter);
            }
        });
        thread.start();
    }

    private void addBtnInit(){
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new GameAddFragment(), "gameAdd")
                        .addToBackStack("gameAdd")
                        .commit();
            }
        });
    }

    private void deleteGame(){

        binding.gamesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        game games = gameAdapter.getItem(i);
                        viewModel.deleteGame(games);
                        removeArtistOnMainThread(games);
                    }
                });
                thread.start();
                return false;
            }
        });
    }

    private void removeArtistOnMainThread(game games) {
        requireActivity().runOnUiThread(() -> {
            gameAdapter.remove(games);
        });
    }

    private void updateGame(){
        binding.gamesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new GameAddFragment(true, gameAdapter.getItem(i)), "gameAdd")
                        .addToBackStack("gameAdd")
                        .commit();
            }
        });
    }
}

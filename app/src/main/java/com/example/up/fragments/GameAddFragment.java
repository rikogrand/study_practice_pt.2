package com.example.up.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.up.database.entities.game;
import com.example.up.database.viewmodels.gameViewModel;
import com.example.up.databinding.FragmentGameAddBinding;

public class GameAddFragment extends Fragment {
    private boolean update;
    private game upd;

    public GameAddFragment(boolean update, game upd) {

        this.update = update;

        this.upd = upd;
    }

    public GameAddFragment() {

        this.update = false;
        this.upd = null;
    }

    FragmentGameAddBinding binding;
    gameViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(gameViewModel.class);
        binding = FragmentGameAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (update) {
            binding.addGameSave.setVisibility(View.GONE);
            binding.addGameName.setText(upd.name);
            binding.addGameYear.setText(String.valueOf(upd.year));
            binding.addGameCreator.setText(upd.creator);
            binding.addGamePublisher.setText(upd.publisher);
            binding.addGameUpdate.setOnClickListener(view1 -> {
                game item = new game();
                item.name = binding.addGameName.getText().toString();
                item.year = Integer.parseInt( binding.addGameYear.getText().toString() );
                item.creator = binding.addGameCreator.getText().toString();
                item.publisher = binding.addGamePublisher.getText().toString();
                Log.d(GameAddFragment.class.getName(), String.format("Updating game with %s %d %s %s", item.name, item.year, item.creator, item.publisher));
                viewModel.updateGame(item);
                getActivity().getSupportFragmentManager().popBackStack();
            });
        } else {

            binding.addGameUpdate.setVisibility(View.GONE);
            binding.addGameSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    game item = new game();
                    item.name = binding.addGameName.getText().toString();
                    item.year = binding.addGameYear.getText().toString().length();
                    item.creator = binding.addGameCreator.getText().toString();
                    item.publisher = binding.addGamePublisher.getText().toString();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            viewModel.addGame(item);
                        }
                    });
                    getActivity().getSupportFragmentManager().popBackStack();
                    thread.start();
                }
            });
        }
    }
}
package com.example.up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.up.databinding.ActivityMainBinding;
import com.example.up.fragments.GameFragment;
import com.example.up.fragments.GameFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment, new GameFragment(), "games")
                .addToBackStack(null)
                .commit();
    }
}
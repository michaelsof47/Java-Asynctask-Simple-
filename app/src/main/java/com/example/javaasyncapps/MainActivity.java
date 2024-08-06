package com.example.javaasyncapps;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaasyncapps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.tvLabel.setText("Empty Data");
        setContentView(view);
        binding.btnAsynctask.setOnClickListener(v -> {
            binding.tvLabel.setVisibility(View.GONE);
            binding.progressLoading.setVisibility(View.VISIBLE);
            new RetrieveUrl(resp -> {
                binding.progressLoading.setVisibility(View.GONE);
                binding.tvLabel.setVisibility(View.VISIBLE);
                binding.tvLabel.setText(resp);
            }).execute();
        });
    }
}
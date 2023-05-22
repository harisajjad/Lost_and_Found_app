package com.example.a71p;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createNewAdvertButton = findViewById(R.id.createNewAdvert);
        createNewAdvertButton.setOnClickListener(view -> {
            startActivity(new Intent(this, CreateNew.class));
        });

        Button showAllItemsButton = findViewById(R.id.showAllItems);
        showAllItemsButton.setOnClickListener(view -> {
            startActivity(new Intent(this, ShowAllAdvert.class));
        });
    }
}
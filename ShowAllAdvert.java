package com.example.a71p;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowAllAdvert extends AppCompatActivity implements SetOnItemClickListener {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private DatabaseClass databaseClass;
    private TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_advert);

        databaseClass = new DatabaseClass(this);
        recyclerView = findViewById(R.id.lostAndFound);
        noData = findViewById(R.id.noData);

        adapter = new Adapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Retrieve data from the database
        ArrayList<LostAndFound> list = databaseClass.getData();

        // Show or hide the "noData" TextView based on the presence of data
        if (list.isEmpty())
            noData.setVisibility(View.VISIBLE);
        else
            noData.setVisibility(View.GONE);

        // Update the adapter with the new data
        adapter.submit(list);
    }

    @Override
    public void onItemClickListener(LostAndFound lostAndFound) {
        Intent intent = new Intent(this, RemoveAdvert.class);
        intent.putExtra("id", lostAndFound.getId());
        startActivity(intent);
    }
}
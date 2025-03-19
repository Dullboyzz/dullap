package com.example.listlayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button btnShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Toolbar Setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Priyanshu"); // Set Title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable Back Button
        }

        btnShowList = findViewById(R.id.btnShowList);
        listView = findViewById(R.id.listView);

        // List Data
        ArrayList<String> items = new ArrayList<>();
        items.add("Priyanshu");
        items.add("Arshiyan");
        items.add("Aditya");
        items.add("Sachin");
        items.add("Abhishek");
        items.add("Kaish");
        items.add("Suraj");

        // Adapter for ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        // Show List on Button Click
        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.VISIBLE); // Show ListView
                btnShowList.setVisibility(View.GONE); // Hide Button
            }
        });
    }

    // Handle Back Button Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close the current activity and go back
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

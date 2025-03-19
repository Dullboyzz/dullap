package com.example.allinone;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnCoordinate).setOnClickListener(view -> startActivity(new Intent(this, CoordinateActivity.class)));
        findViewById(R.id.btnLinear).setOnClickListener(view -> startActivity(new Intent(this, LinearActivity.class)));
        findViewById(R.id.btnRelative).setOnClickListener(view -> startActivity(new Intent(this, RelativeActivity.class)));
        findViewById(R.id.btnTable).setOnClickListener(view -> startActivity(new Intent(this, TableActivity.class)));
        findViewById(R.id.btnFrame).setOnClickListener(view -> startActivity(new Intent(this, FrameActivity.class)));
        findViewById(R.id.btnListView).setOnClickListener(view -> startActivity(new Intent(this, ListViewActivity.class)));
        findViewById(R.id.btnGridView).setOnClickListener(view -> startActivity(new Intent(this, GridViewActivity.class)));
    }
}
package com.example.activitiesandfragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnone=findViewById(R.id.btnone);


        btnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Secondpage.class);
                startActivity(intent);
            }
        });
        }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Activity LifeCycle","onStart Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Activity LifeCycle","onResume Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Activity LifeCycle","onPause Called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Activity LifeCycle","onStop Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Activity LifeCycle","onDestroy Called");
    }
}
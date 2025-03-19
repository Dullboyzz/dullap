package com.example.table;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        setButtonClickListener(R.id.btn1);
        setButtonClickListener(R.id.btn2);
        setButtonClickListener(R.id.btn3);
        setButtonClickListener(R.id.btn4);
        setButtonClickListener(R.id.btn5);
        setButtonClickListener(R.id.btn6);
        setButtonClickListener(R.id.btn7);
        setButtonClickListener(R.id.btn8);
        setButtonClickListener(R.id.btn9);
    }

    private void setButtonClickListener(int buttonId) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button " + button.getText() + " clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
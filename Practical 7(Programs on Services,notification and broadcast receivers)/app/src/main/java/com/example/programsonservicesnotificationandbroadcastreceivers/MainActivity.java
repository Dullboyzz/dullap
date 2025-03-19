package com.example.programsonservicesnotificationandbroadcastreceivers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyBroadcastReceiver receiver;

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar Setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Android Services & Receivers");

        // Initialize Buttons
        Button btnStartService = findViewById(R.id.btnStartService);
        Button btnShowNotification = findViewById(R.id.btnShowNotification);
        Button btnSendBroadcast = findViewById(R.id.btnSendBroadcast);

        // Start Foreground Service
        btnStartService.setOnClickListener(view -> {
            Intent serviceIntent = new Intent(this, MyService.class);
            startService(serviceIntent);
            Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        });

        // Show Notification
        btnShowNotification.setOnClickListener(view -> {
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);
        });

        // Send Broadcast
        btnSendBroadcast.setOnClickListener(view -> {
            Intent intent = new Intent("com.example.myapp.MY_BROADCAST");
            sendBroadcast(intent);
        });

        // Register Broadcast Receiver with Android 12+ Fix
        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.example.myapp.MY_BROADCAST");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(receiver, filter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}

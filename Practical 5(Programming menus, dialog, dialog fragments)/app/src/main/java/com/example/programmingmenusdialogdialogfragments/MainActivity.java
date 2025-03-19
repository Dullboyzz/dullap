package com.example.programmingmenusdialogdialogfragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    private ContentResolver contentResolver;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Elements
        Button btnShowDialog = findViewById(R.id.btnShowDialog);
        Button btnShowDialogFragment = findViewById(R.id.btnShowDialogFragment);
        Button btnBrightnessControl = findViewById(R.id.btnBrightnessControl);
        contentResolver = getContentResolver();

        // Setup Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu and Dialog");

        // Set Button Click Listeners
        btnShowDialog.setOnClickListener(v -> showAlertDialog());
        btnShowDialogFragment.setOnClickListener(v -> showDialogFragment());
        btnBrightnessControl.setOnClickListener(v -> showBrightnessDialog());

        // Check and Request Permission for Brightness Control
        if (!Settings.System.canWrite(this)) {
            requestBrightnessPermission();
        }
    }

    private void requestBrightnessPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(android.net.Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.action_about) {
            Toast.makeText(this, "About selected", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Alert Dialog")
                .setMessage("Do you want to proceed?")
                .setPositiveButton("Yes", (dialog, which) ->
                        Toast.makeText(MainActivity.this, "You pressed Yes", Toast.LENGTH_SHORT).show()
                )
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showDialogFragment() {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "MyDialogFragment");
    }

    private void showBrightnessDialog() {
        BrightnessDialogFragment brightnessDialog = new BrightnessDialogFragment();
        brightnessDialog.show(getSupportFragmentManager(), "BrightnessDialog");
    }
}

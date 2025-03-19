package com.example.databaseprogrammingwithsqlite;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText userIdEditText, userNameEditText, userAgeEditText;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    TextView dataCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the title programmatically
        getSupportActionBar().setTitle("Database");

        dbHelper = new DatabaseHelper(this);
        userIdEditText = findViewById(R.id.editTextUserId);
        userNameEditText = findViewById(R.id.editTextUserName);
        userAgeEditText = findViewById(R.id.editTextUserAge);
        recyclerView = findViewById(R.id.recyclerViewUsers);
        dataCountTextView = findViewById(R.id.textViewDataCount);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonShowAll = findViewById(R.id.buttonShowAll);

        // Add User
        buttonAdd.setOnClickListener(v -> {
            String name = userNameEditText.getText().toString();
            String ageStr = userAgeEditText.getText().toString();

            if (!name.isEmpty() && !ageStr.isEmpty()) {
                int age = Integer.parseInt(ageStr);
                dbHelper.insertUser(name, age);
                userNameEditText.setText("");
                userAgeEditText.setText("");
                fetchData();
            } else {
                Toast.makeText(MainActivity.this, "Please enter both name and age", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete User
        buttonDelete.setOnClickListener(v -> {
            String userIdStr = userIdEditText.getText().toString();

            if (!userIdStr.isEmpty()) {
                int id = Integer.parseInt(userIdStr);

                // Check if the user exists before trying to delete
                Cursor cursor = dbHelper.getAllUsers();
                boolean userExists = false;
                while (cursor.moveToNext()) {
                    int userId = cursor.getInt(cursor.getColumnIndex("id"));
                    if (userId == id) {
                        userExists = true;
                        break;
                    }
                }

                if (userExists) {
                    dbHelper.deleteUser(id);
                    userIdEditText.setText("");
                    fetchData();
                    Toast.makeText(MainActivity.this, "User deleted successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "User with this ID does not exist", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            } else {
                Toast.makeText(MainActivity.this, "Please enter a valid User ID", Toast.LENGTH_SHORT).show();
            }
        });

        // Show All Users
        buttonShowAll.setOnClickListener(v -> fetchData());
    }

    private void fetchData() {
        Cursor cursor = dbHelper.getAllUsers();
        userAdapter = new UserAdapter(this, cursor);
        recyclerView.setAdapter(userAdapter);

        // Update data count TextView
        int count = cursor.getCount();
        dataCountTextView.setText("Fetched Data Count: " + count);
    }
}

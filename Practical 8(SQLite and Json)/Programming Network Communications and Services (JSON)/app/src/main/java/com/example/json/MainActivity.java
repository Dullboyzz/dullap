package com.example.json;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.json.Adapter.EmployeeAdapter;
import com.example.json.model.Employee;
import com.example.json.network.EmployeeApi;
import com.example.json.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etPosition, etSalary;
    private Button btnAddEmployee;
    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private List<Employee> employeeList;
    private EmployeeApi employeeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etPosition = findViewById(R.id.etPosition);
        etSalary = findViewById(R.id.etSalary);
        btnAddEmployee = findViewById(R.id.btnAddEmployee);
        recyclerView = findViewById(R.id.employeeRecyclerView);

        // Retrofit API call setup
        employeeApi = RetrofitClient.getRetrofitInstance().create(EmployeeApi.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        employeeList = new ArrayList<>();
        adapter = new EmployeeAdapter(this, employeeList, employeeApi);
        recyclerView.setAdapter(adapter);

        // Add Employee
        btnAddEmployee.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String position = etPosition.getText().toString().trim();
            String salaryStr = etSalary.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(position) || TextUtils.isEmpty(salaryStr)) {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int salary;
            try {
                salary = Integer.parseInt(salaryStr);
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Invalid salary input", Toast.LENGTH_SHORT).show();
                return;
            }

            Employee employee = new Employee();
            employee.setName(name);
            employee.setPosition(position);
            employee.setSalary(salary);

            employeeApi.addEmployee(employee).enqueue(new Callback<Employee>() {
                @Override
                public void onResponse(Call<Employee> call, Response<Employee> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        employeeList.add(response.body());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Employee added successfully", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to add employee", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Employee> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        getEmployees();
    }

    private void getEmployees() {
        employeeApi.getAllEmployees().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    employeeList = response.body();
                    adapter.updateData(employeeList);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load employees", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearInputFields() {
        etName.setText("");
        etPosition.setText("");
        etSalary.setText("");
    }
}

package com.example.json.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.json.R;
import com.example.json.model.Employee;
import com.example.json.network.EmployeeApi;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> employeeList;
    private EmployeeApi employeeApi;
    private Context context;

    public EmployeeAdapter(Context context, List<Employee> employeeList, EmployeeApi employeeApi) {
        this.context = context;
        this.employeeList = employeeList;
        this.employeeApi = employeeApi;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Employee employee = employeeList.get(position);
        holder.tvName.setText(employee.getName());
        holder.tvPosition.setText(employee.getPosition());
        holder.tvSalary.setText("₹ " + employee.getSalary());

        // Delete Employee
        holder.btnDelete.setOnClickListener(v -> {
            Call<Void> call = employeeApi.deleteEmployee(employee);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        employeeList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Employee deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to delete employee", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Update Employee
        holder.btnUpdate.setOnClickListener(v -> showUpdateDialog(employee, position));
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public void updateData(List<Employee> newEmployeeList) {
        this.employeeList = newEmployeeList;
        notifyDataSetChanged();
    }

    private void showUpdateDialog(Employee employee, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_employee, null);
        builder.setView(dialogView);

        EditText etUpdateName = dialogView.findViewById(R.id.etUpdateName);
        EditText etUpdatePosition = dialogView.findViewById(R.id.etUpdatePosition);
        EditText etUpdateSalary = dialogView.findViewById(R.id.etUpdateSalary);
        Button btnUpdateConfirm = dialogView.findViewById(R.id.btnUpdateConfirm);

        etUpdateName.setText(employee.getName());
        etUpdatePosition.setText(employee.getPosition());
        etUpdateSalary.setText(String.valueOf(employee.getSalary()));

        AlertDialog dialog = builder.create();
        dialog.show();

        btnUpdateConfirm.setOnClickListener(v -> {
            String newName = etUpdateName.getText().toString().trim();
            String newPosition = etUpdatePosition.getText().toString().trim();
            int newSalary = Integer.parseInt(etUpdateSalary.getText().toString().trim());

            Employee updatedEmployee = new Employee();
            updatedEmployee.setName(employee.getName()); // Use existing name to identify employee
            updatedEmployee.setNewName(newName);
            updatedEmployee.setPosition(newPosition);
            updatedEmployee.setSalary(newSalary);

            employeeApi.updateEmployee(updatedEmployee).enqueue(new Callback<Employee>() {
                @Override
                public void onResponse(Call<Employee> call, Response<Employee> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (position < employeeList.size()) {  // Check index validity
                            employeeList.set(position, response.body());
                            notifyItemChanged(position);
                            Toast.makeText(context, "Employee updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error: Invalid index", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Failed to update employee", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Employee> call, Throwable t) {
                    Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPosition, tvSalary;
        Button btnDelete, btnUpdate;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPosition = itemView.findViewById(R.id.tvPosition);
            tvSalary = itemView.findViewById(R.id.tvSalary);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}

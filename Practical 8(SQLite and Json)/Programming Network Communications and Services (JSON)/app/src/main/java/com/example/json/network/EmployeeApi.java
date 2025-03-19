package com.example.json.network;

import com.example.json.model.Employee;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface EmployeeApi {
    @POST("employees")
    Call<Employee> addEmployee(@Body Employee employee);

    @GET("employees")
    Call<List<Employee>> getAllEmployees();

    @PUT("employees")
    Call<Employee> updateEmployee(@Body Employee employee);

    @HTTP(method = "DELETE", path = "employees", hasBody = true)
    Call<Void> deleteEmployee(@Body Employee employee);
}


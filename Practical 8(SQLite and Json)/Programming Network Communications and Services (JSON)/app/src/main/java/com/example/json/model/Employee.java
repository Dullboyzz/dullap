package com.example.json.model;

public class Employee {
    private String id;
    private String name;
    private String newName;
    private String position;
    private int salary;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getNewName() {  // Add this method
        return newName;
    }

    public void setNewName(String newName) {  // Add this method
        this.newName = newName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}

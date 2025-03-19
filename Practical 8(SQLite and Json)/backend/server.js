const express = require("express");
const mongoose = require("mongoose");
const dotenv = require("dotenv");

dotenv.config();

const app = express();
app.use(express.json());

mongoose.connect(process.env.MONGODB_URI, { useNewUrlParser: true, useUnifiedTopology: true });

const employeeSchema = new mongoose.Schema({
    name: String,
    position: String,
    salary: Number
});

const Employee = mongoose.model("Employee", employeeSchema);

// Add Employee
app.post("/employees", async (req, res) => {
    const { name, position, salary } = req.body;
    const employee = new Employee({ name, position, salary });
    await employee.save();
    res.status(201).send(employee);
});

// Get all Employees
app.get("/employees", async (req, res) => {
    const employees = await Employee.find();
    res.status(200).send(employees);
});

// Update Employee by Name
app.put("/employees", async (req, res) => {
    const { name, newName, position, salary } = req.body;
    
    if (!name) {
        return res.status(400).send({ message: "Employee name is required for updating" });
    }

    const updatedEmployee = await Employee.findOneAndUpdate(
        { name },  // Find employee by name
        { name: newName || name, position, salary },  // Update details
        { new: true }
    );

    if (!updatedEmployee) {
        return res.status(404).send({ message: "Employee not found" });
    }

    res.status(200).send(updatedEmployee);
});


// Delete Employee by Name
app.delete("/employees", async (req, res) => {
    const { name } = req.body;
    if (!name) {
        return res.status(400).send({ message: "Employee name is required for deletion" });
    }
    const deletedEmployee = await Employee.findOneAndDelete({ name });
    if (!deletedEmployee) {
        return res.status(404).send({ message: "Employee not found" });
    }
    res.status(200).send({ message: `Employee ${name} deleted successfully` });
});



const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});

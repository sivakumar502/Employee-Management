package com.example.controller;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Get all employees
    @GetMapping("/allEmployees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Get employee by id
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    // Create a new employee
    @PostMapping("/addEmployee")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody @Valid Employee employee) {
        return employeeService.createEmployee(employee);
    }

    // Update an existing employee
    @PutMapping("update/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody @Valid Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}

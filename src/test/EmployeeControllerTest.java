package com.example.controller;

import com.example.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl = "/api/employees";

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = new Employee("John Doe", "HR");
    }

    @Test
    public void testCreateEmployee() {
        ResponseEntity<Employee> response = restTemplate.postForEntity(baseUrl, employee, Employee.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals(employee.getName(), response.getBody().getName());
    }

    @Test
    public void testGetEmployeeById() {
        Employee createdEmployee = restTemplate.postForObject(baseUrl, employee, Employee.class);
        ResponseEntity<Employee> response = restTemplate.getForEntity(baseUrl + "/" + createdEmployee.getId(), Employee.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAllEmployees() {
        restTemplate.postForEntity(baseUrl, employee, Employee.class);
        ResponseEntity<Employee[]> response = restTemplate.getForEntity(baseUrl, Employee[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    public void testDeleteEmployee() {
        Employee createdEmployee = restTemplate.postForObject(baseUrl, employee, Employee.class);
        restTemplate.delete(baseUrl + "/" + createdEmployee.getId());
        ResponseEntity<Employee> response = restTemplate.getForEntity(baseUrl + "/" + createdEmployee.getId(), Employee.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

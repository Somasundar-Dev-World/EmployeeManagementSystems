package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.EmployeeRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired(required = false)
    private EmployeeRepository employeeRepository;
    @Autowired(required = false)
    private EmployeeRepositoryMongo employeeRepositoryMongo;

    @Value("${spring.profiles.active:local}")
    private String activeProfile;

    private boolean isLocalProfile() {
        return "local".equalsIgnoreCase(activeProfile);
    }

    public List<Employee> getAllEmployees() {
        if (isLocalProfile()) {
            return employeeRepository.findAll();
        } else {
            return employeeRepositoryMongo.findAll();
        }
    }

    public List<Employee> createEmployees(List<Employee> employees) {
        if (isLocalProfile()) {
            return employeeRepository.saveAll(employees);
        } else {
            return employeeRepositoryMongo.saveAll(employees);
        }
    }

    public Optional<Employee> getEmployeeById(String id) {
        if (isLocalProfile()) {
            return employeeRepository.findById(id);
        } else {
            return employeeRepositoryMongo.findById(id);
        }
    }

    public Employee createEmployee(Employee employee) {
        if (isLocalProfile()) {
            return employeeRepository.save(employee);
        } else {
            return employeeRepositoryMongo.save(employee);
        }
    }

    public Optional<Employee> updateEmployee(String id, Employee employeeDetails) {
        if (isLocalProfile()) {
            return employeeRepository.findById(id).map(employee -> {
                employee.setName(employeeDetails.getName());
                employee.setEmail(employeeDetails.getEmail());
                employee.setDepartment(employeeDetails.getDepartment());
                return employeeRepository.save(employee);
            });
        } else {
            return employeeRepositoryMongo.findById(id).map(employee -> {
                employee.setName(employeeDetails.getName());
                employee.setEmail(employeeDetails.getEmail());
                employee.setDepartment(employeeDetails.getDepartment());
                return employeeRepositoryMongo.save(employee);
            });
        }
    }

    public boolean deleteEmployee(String id) {
        if (isLocalProfile()) {
            return employeeRepository.findById(id).map(employee -> {
                employeeRepository.delete(employee);
                return true;
            }).orElse(false);
        } else {
            return employeeRepositoryMongo.findById(id).map(employee -> {
                employeeRepositoryMongo.delete(employee);
                return true;
            }).orElse(false);
        }
    }
}

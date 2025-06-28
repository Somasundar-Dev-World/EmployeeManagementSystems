package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepositoryMongo extends MongoRepository<Employee, String> {
}

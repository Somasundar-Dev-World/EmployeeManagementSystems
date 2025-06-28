package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"dev", "sit", "uat", "prod"})
public interface EmployeeRepositoryMongo extends MongoRepository<Employee, String> {
}


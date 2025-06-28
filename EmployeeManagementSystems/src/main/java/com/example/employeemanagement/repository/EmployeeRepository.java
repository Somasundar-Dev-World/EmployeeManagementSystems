package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}

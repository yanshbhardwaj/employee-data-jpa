package com.example.employeedatajpa.dao;

import com.example.employeedatajpa.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    public Employee findById(int id);
}

package com.example.employeedatajpa.services;

import com.example.employeedatajpa.dao.EmployeeRepository;
import com.example.employeedatajpa.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Component
public class EmployeeService {

    Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Employee> getAllEmployee(Optional<Integer> page, Optional<Integer> size, @RequestParam Optional<String> order, Optional<String> sort){
        logger.trace("getAllEMployee method trace log");
        Page<Employee> result = employeeRepository.findAll(PageRequest.of(
                page.orElse(1), size.orElse(5),
                Sort.Direction.valueOf(order.orElse("ASC")), sort.orElse("empId")));
        if(result.isEmpty())
            logger.warn("No data found");
        return result;
    }

    public Employee getEmployeeById(int id){
        Employee employee = null;
        try{
            employee = this.employeeRepository.findById(id);
        }
        catch (Exception e){
            logger.error("Error occurred in fetching data by id");
            e.printStackTrace();
        }
        return employee;
    }

    public Employee addEmployee(Employee employee){
        logger.trace("Adding employee data");
        Employee result = employeeRepository.save(employee);
        return result;
    }
}

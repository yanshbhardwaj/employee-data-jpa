package com.example.employeedatajpa.controller;

import com.example.employeedatajpa.entity.Employee;
import com.example.employeedatajpa.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") int id){
        Employee employee = employeeService.getEmployeeById(id);
        if (employee==null) {
            logger.warn("No data found when fetching by id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(employee));
    }

    @PostMapping("/employee/employeedata")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        Employee employee1 = null;

        try{
            employee1 = this.employeeService.addEmployee(employee);
            System.out.println(employee);
            restTemplate.exchange("http://localhost:8081/publish/", HttpMethod.POST, null, String.class).getBody();
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (Exception e){
            logger.error("Failed to add employee data some error occurred");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/employee")
    List<Employee> getPagedEmployees(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size,
                                     @RequestParam Optional<String> order, @RequestParam Optional<String> sort ){
        return employeeService.getAllEmployee(page, size, order, sort).getContent();

    }
}

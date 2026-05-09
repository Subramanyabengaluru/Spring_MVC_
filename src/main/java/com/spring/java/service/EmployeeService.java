package com.spring.java.service;

import com.spring.java.dto.EmployeeRequest;
import com.spring.java.dto.EmployeeResponse;
import com.spring.java.entity.Employee;
import com.spring.java.exception.ResourceNotFoundException;
import com.spring.java.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    private final ModelMapper mapper;

    // CREATE
    public EmployeeResponse saveEmployee(EmployeeRequest employeeRequest) {

        Employee employee =
                mapper.map(employeeRequest, Employee.class);

        Employee savedEmployee =
                employeeRepo.save(employee);

        return mapper.map(savedEmployee, EmployeeResponse.class);
    }

    // GET ALL
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepo.findAll()
                .stream()
                .map(employee ->
                        mapper.map(employee, EmployeeResponse.class))
                .toList();
    }

    // GET BY ID
    public EmployeeResponse getEmployeeById(Long empId) {

        Employee employee = employeeRepo.findById(empId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + empId));

        return mapper.map(employee, EmployeeResponse.class);
    }

    // UPDATE
    public EmployeeResponse updateEmployee(
            Long empId,
            EmployeeRequest employeeRequest) {

        Employee existingEmployee = employeeRepo.findById(empId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + empId));

        mapper.map(employeeRequest, existingEmployee);

        Employee updatedEmployee =
                employeeRepo.save(existingEmployee);

        return mapper.map(updatedEmployee, EmployeeResponse.class);
    }

    // DELETE
    public String deleteEmployee(Long empId) {

        Employee employee = employeeRepo.findById(empId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + empId));

        employeeRepo.delete(employee);

        return "Employee deleted successfully with id: " + empId;
    }
}
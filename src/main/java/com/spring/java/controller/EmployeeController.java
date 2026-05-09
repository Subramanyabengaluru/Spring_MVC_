package com.spring.java.controller;

import com.spring.java.dto.EmployeeRequest;
import com.spring.java.dto.EmployeeResponse;
import com.spring.java.dto.ApiResponse;
import com.spring.java.entity.Employee;
import com.spring.java.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    /*
    createEmployee
    getAllEmployees
    getEmployeeById
    updateEmployee
    deleteEmployee
     */

    //ResponseEntity

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest){
        EmployeeResponse employeeResponse = employeeService.saveEmployee(employeeRequest);

        ApiResponse<EmployeeResponse> response = ApiResponse.<EmployeeResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Employee created successfully")
                .data(employeeResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees(){
        List<EmployeeResponse> employeeResponse = employeeService.getAllEmployees();

        ApiResponse<List<EmployeeResponse>> response = ApiResponse.<List<EmployeeResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Employees retrieved successfully")
                .data(employeeResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{empId}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(@PathVariable Long empId){
        EmployeeResponse employeeResponse = employeeService.getEmployeeById(empId);

        ApiResponse<EmployeeResponse> response = ApiResponse.<EmployeeResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Employee retrieved successfully")
                .data(employeeResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{empId}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(@PathVariable Long empId, @Valid @RequestBody EmployeeRequest employeeRequest){
        EmployeeResponse employeeResponse = employeeService.updateEmployee(empId, employeeRequest);

        ApiResponse<EmployeeResponse> response = ApiResponse.<EmployeeResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Employee updated successfully")
                .data(employeeResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{empId}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable Long empId){
        String result = employeeService.deleteEmployee(empId);

        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Employee deleted successfully")
                .data(result)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//     findAllByOrderByFirstName

    @GetMapping("/findAllByOrderByFirstName")
    public List<Employee> findAllByOrderByFirstName(){
        return employeeService.findAllByOrderByFirstName();
    }



}

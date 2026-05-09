package com.spring.java.repository;

import com.spring.java.entity.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {

    List<Employee> findAllByOrderByFirstName();

    List<Employee> findBy(Sort sortBy);
}

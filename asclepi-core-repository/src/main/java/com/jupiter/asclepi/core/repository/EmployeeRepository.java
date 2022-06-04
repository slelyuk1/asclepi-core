package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.repository.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}

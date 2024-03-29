package com.jupiter.asclepi.core.repository;

import com.jupiter.asclepi.core.model.entity.people.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}

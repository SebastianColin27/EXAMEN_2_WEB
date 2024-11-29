package com.examenII.examenII.repositories;

import com.examenII.examenII.models.EmployeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends JpaRepository<EmployeesEntity, Long> {
}

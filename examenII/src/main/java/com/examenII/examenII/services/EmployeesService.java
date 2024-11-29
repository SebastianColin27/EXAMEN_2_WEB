package com.examenII.examenII.services;

import com.examenII.examenII.models.EmployeesEntity;
import com.examenII.examenII.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeesService {
    @Autowired
    EmployeesRepository employeesRepository;

    public List<EmployeesEntity> getAllEmployees(){
        return employeesRepository.findAll();
    }

    public Optional<EmployeesEntity> getEmployeeById(Long id){
        return employeesRepository.findById(id);
    }


    public EmployeesEntity createEmployee(EmployeesEntity employees){
        return employeesRepository.save(employees);
    }


    public EmployeesEntity updateEmployee(EmployeesEntity employee){
        return employeesRepository.save(employee);
    }

    public void deleteEmployee(Long id){
        employeesRepository.deleteById(id);
    }


}
/*
    private final EmployeesRepository employeesRepository;

    @Autowired
    public EmployeesService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    // List all employees
    @Transactional(readOnly = true)
    public List<EmployeesEntity> obtenerTodos() {
        return employeesRepository.findAll();
    }

    // Save a new employee
    @Transactional
    public EmployeesEntity guardarEmployee(EmployeesEntity employees) {
        return employeesRepository.save(employees);
    }

    // Update an existing employee
    @Transactional
    public EmployeesEntity actualizarEmployee(Long id, EmployeesEntity employees) {


        // Update fields from employeeDetails to existingEmployee
        // This assumes you have appropriate setters in your EmployeesEntity

        // Add other fields as necessary

        return employeesRepository.save(employees);
    }

    // Get employee by ID
    @Transactional(readOnly = true)
    public EmployeesEntity obtenerEmployeePorId(Long id) {
        return employeesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    // Delete an employee
    @Transactional
    public void eliminarEmployee(Long id) {
        EmployeesEntity employee = obtenerEmployeePorId(id);
        employeesRepository.delete(employee);
    }*/



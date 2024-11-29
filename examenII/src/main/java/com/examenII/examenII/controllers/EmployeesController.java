package com.examenII.examenII.controllers;

import com.examenII.examenII.models.EmployeesEntity;
import com.examenII.examenII.services.EmployeesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/employees")

@Tag(
        name = "Employees"
)
public class EmployeesController {
    private EmployeesService employeesService;


    @GetMapping
public ResponseEntity<CustomResponse<List<EmployeesEntity>>> getEmployee() {
        List<EmployeesEntity> employees;
        Link selfLink = linkTo(methodOn(EmployeesController.class).getEmployee()).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            employees = employeesService.getAllEmployees();
            if (!employees.isEmpty()) {
                return ResponseEntity.ok(new CustomResponse<>(1, "Employees encontrados", employees, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "No se encontraron employees", employees, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<EmployeesEntity>> getEmployeeById(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(EmployeesController.class).getEmployeeById(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            Optional<EmployeesEntity> employee = employeesService.getEmployeeById(id);
            if (employee.isPresent()) {
                return ResponseEntity.ok(new CustomResponse<>(1, "Employee encontrado", employee.get(), links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Employee no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<EmployeesEntity>> createEmployee(@RequestBody EmployeesEntity employee) {
        Link selfLink = linkTo(methodOn(EmployeesController.class).createEmployee(employee)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            EmployeesEntity nuevoEmployee = employeesService.createEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomResponse<>(1, "Employee creado exitosamente", nuevoEmployee, links));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al crear un employee", null, links));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<EmployeesEntity>> updateEmployee(
            @PathVariable Long id, @RequestBody EmployeesEntity employee) {
        Link selfLink = linkTo(methodOn(EmployeesController.class).updateEmployee(id, employee)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            employee.setId(id);
            if (employeesService.getEmployeeById(id).isPresent()) {
                EmployeesEntity employeeActualizado = employeesService.updateEmployee(employee);
                return ResponseEntity.ok(new CustomResponse<>(1, "Employee actualizado exitosamente", employeeActualizado, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Employeea no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al actualizar un employeea", null, links));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteEmployeea(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(EmployeesController.class).deleteEmployeea(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            if (employeesService.getEmployeeById(id).isPresent()) {
                employeesService.deleteEmployee(id);
                return ResponseEntity.ok(new CustomResponse<>(1, "Employee eliminado exitosamente", null, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Employee no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al eliminar un Employee", null, links));
        }
    }
}

 /*   public EmployeesController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    @Operation(summary = "List all employees")
    public ResponseEntity<List<EmployeesEntity>> getAllEmployees() {
        List<EmployeesEntity> employees = employeesService.obtenerTodos();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    @Operation(summary = "Get employee by ID")
    public ResponseEntity<EmployeesEntity> getEmployeeById(@PathVariable Long id) {
        EmployeesEntity employee = employeesService.obtenerEmployeePorId(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE-USER')")  // Changed from 'CREATE' to match SecurityConfig
    @Operation(summary = "Create a new employee")
    public ResponseEntity<EmployeesEntity> createEmployee(@RequestBody EmployeesEntity employee) {
        EmployeesEntity savedEmployee = employeesService.guardarEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE')")
    @Operation(summary = "Update an existing employee")
    public ResponseEntity<EmployeesEntity> updateEmployee(@PathVariable Long id, @RequestBody EmployeesEntity employee) {
        EmployeesEntity updatedEmployee = employeesService.actualizarEmployee(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE')")
    @Operation(summary = "Delete an employee")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeesService.eliminarEmployee(id);
        return ResponseEntity.noContent().build();
    }
}*/
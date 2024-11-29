package com.examenII.examenII.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="employees")
public class EmployeesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String first_name;

    @NotBlank
    @Size(max = 100)
    private String last_name;

    @NotBlank
    private Date hire_date;

    @Size(max = 100)
    private String position;



}

package com.examenII.examenII.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info=@Info(
                title="API de Employees",
                description="Esta api proporciona accersoa los recurisos de los empleados de abarrotes Don Sebas",
                version = "1.0.0",
                contact = @Contact(
                        name="Sebastian Colin",
                        email = "jcolinb1800@alumno.ipn.mx",
                        url = "http://localhost:8086/contacto"
                ),
                license = @License(),
                termsOfService = ""
        ),
        servers ={
                @Server(
                        description = "Servidor de puebas",
                        url = "http://pruebas.com:8080/api/v1"),
                @Server(
                        description = "Servidor de producción",
                        url = "http://localhost:8095/api/v1/employees")
        },
        tags = {
                @Tag(
                        name = "Employees",
                        description = "Endpoints para los recursos del empleado"
                )
        }


)


public class OpenApiConfig {
}

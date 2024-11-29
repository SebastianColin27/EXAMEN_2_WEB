package com.examenII.examenII.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.ArrayList;
import java.util.List;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permite el uso de @PreAuthorize en métodos
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    // Constructor injection instead of @Autowired
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable() // Deshabilitar CSRF para facilitar pruebas (habilítalo en producción)
                .httpBasic(Customizer.withDefaults()) // Autenticación básica
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless para APIs REST
                .authorizeHttpRequests(auth -> {
                    // Configuración de permisos por endpoint
                    auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").hasAuthority("CREATE-USER");
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/employees/**").hasAuthority("READ");
                    auth.requestMatchers(HttpMethod.POST, "/api/v1/employees/**").hasAuthority("CREATE-USER");
                    auth.requestMatchers(HttpMethod.PUT, "/api/v1/employees/**").hasAuthority("UPDATE");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/v1/employees/**").hasAuthority("DELETE");
                    // Permitir acceso al resto de los endpoints solo a usuarios autenticados
                    auth.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Codificador de contraseñas
        daoAuthenticationProvider.setUserDetailsService(userDetailsService()); // Servicio de detalles del usuario
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Replace NoOpPasswordEncoder with BCryptPasswordEncoder
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Create users with encoded passwords
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin1234"))
                .authorities("READ", "CREATE-USER", "UPDATE", "DELETE")
                .build();

        UserDetails user1 = User.withUsername("user")
                .password(passwordEncoder().encode("user1234"))
                .authorities("READ")
                .build();

        UserDetails moderador = User.withUsername("moderador")
                .password(passwordEncoder().encode("moderador1234"))
                .authorities("READ", "UPDATE")
                .build();

        UserDetails editor = User.withUsername("editor")
                .password(passwordEncoder().encode("editor1234"))
                .authorities("UPDATE")
                .build();

        UserDetails developer = User.withUsername("developer")
                .password(passwordEncoder().encode("developer1234"))
                .authorities("READ", "WRITE", "UPDATE", "DELETE", "CREATE-USER")
                .build();

        UserDetails analyst = User.withUsername("analista")
                .password(passwordEncoder().encode("analyst1234"))
                .authorities("READ", "DELETE")
                .build();

        return new InMemoryUserDetailsManager(admin, user1, moderador, editor, developer, analyst);
    }
}

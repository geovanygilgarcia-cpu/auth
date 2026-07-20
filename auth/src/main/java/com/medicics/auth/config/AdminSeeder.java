package com.medicics.auth.config;

import com.medicics.auth.model.Rol;
import com.medicics.auth.model.Usuario;
import com.medicics.auth.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Crea un usuario ADMIN inicial al levantar la app, únicamente si la tabla
 * de usuarios está vacía. Resuelve el "huevo o la gallina": crear usuarios
 * requiere ser ADMIN (ver SecurityConfig), pero para ser ADMIN alguien te
 * tiene que haber creado antes.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.nombre}")
    private String adminNombre;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            log.info("Ya existen usuarios en la base de datos, se omite el seeder de admin.");
            return;
        }

        Usuario admin = Usuario.builder()
                .email(adminEmail)
                .passwordHash(passwordEncoder.encode(adminPassword))
                .nombreCompleto(adminNombre)
                .rol(Rol.ADMIN)
                .activo(true)
                .build();

        usuarioRepository.save(admin);

        log.warn("=======================================================");
        log.warn(" Usuario ADMIN creado automáticamente:");
        log.warn("   email:    {}", adminEmail);
        log.warn("   password: {}", adminPassword);
        log.warn(" Cambia esta contraseña en cuanto entres por primera vez.");
        log.warn("=======================================================");
    }
}

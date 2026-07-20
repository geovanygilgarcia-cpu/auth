package com.medicics.auth.dto.usuarios.request;

import com.medicics.auth.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(

        @NotBlank(message = "El correo es requerido")
        @Email(message = "El correo no tiene un formato válido")
        String email,

        @NotBlank(message = "La contraseña es requerida")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,

        @NotBlank(message = "El nombre completo es requerido")
        String nombreCompleto,

        // Opcional: solo aplica a MEDICO, pero no lo forzamos a nivel DTO
        // para no bloquear la creación de otros roles.
        String cedulaProfesional,

        String especialidad,

        String subespecialidad,

        @NotNull(message = "El rol es requerido")
        Rol rol
) {
}

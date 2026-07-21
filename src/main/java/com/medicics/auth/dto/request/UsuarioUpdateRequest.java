package com.medicics.auth.dto.request;

import com.medicics.auth.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateRequest(

        @NotBlank(message = "El correo es requerido")
        @Email(message = "El correo no tiene un formato válido")
        String email,

        // Opcional: si viene null o vacío, se conserva la contraseña actual.
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,

        @NotBlank(message = "El nombre completo es requerido")
        String nombreCompleto,

        String sexo,

        String cedulaProfesional,

        String especialidad,

        String subespecialidad,

        @NotNull(message = "El rol es requerido")
        Rol rol,

        @NotNull(message = "Debes indicar si el usuario está activo")
        Boolean activo
) {
}

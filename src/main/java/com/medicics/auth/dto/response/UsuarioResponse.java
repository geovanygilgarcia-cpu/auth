package com.medicics.auth.dto.response;

import com.medicics.auth.model.Rol;
import com.medicics.auth.model.Sexo;
import com.medicics.auth.model.Usuario;

public record UsuarioResponse(
        String id,
        String email,
        String nombreCompleto,
        Sexo sexo,
        String cedulaProfesional,
        String especialidad,
        String subespecialidad,
        Rol rol,
        boolean activo
) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombreCompleto(),
                usuario.getSexo(),
                usuario.getCedulaProfesional(),
                usuario.getEspecialidad(),
                usuario.getSubespecialidad(),
                usuario.getRol(),
                usuario.isEnabled()
        );
    }
}

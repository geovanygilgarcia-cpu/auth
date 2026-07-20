package com.medicics.auth.dto.response;

/**
 * OJO: esta forma (token, rol, nombreCompleto) ya está espejada en el
 * frontend Angular (auth.model.ts / auth.service.ts). Si le agregas campos
 * aquí, agrégalos también allá o el login dejará de funcionar en silencio.
 */
public record LoginResponse(
        String token,
        String rol,
        String nombreCompleto,
        String especialidad,
        String subespecialidad
) {
}

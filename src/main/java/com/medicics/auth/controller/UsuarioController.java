package com.medicics.auth.controller;

import com.medicics.auth.dto.request.UsuarioRequest;
import com.medicics.auth.dto.request.UsuarioUpdateRequest;
import com.medicics.auth.dto.response.UsuarioResponse;
import com.medicics.auth.model.Rol;
import com.medicics.auth.service.IUsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Protegido método a método (no a nivel de clase): crear/actualizar/eliminar
 * y consultar un usuario puntual son solo ADMIN, pero el LISTADO también lo
 * necesita un MEDICO para poder referir pacientes a otros médicos (ver
 * ReferenciaService en expmedic). Debe coincidir con las reglas de
 * SecurityConfig: el GET "/api/auth/usuarios" (sin id) permite ADMIN y
 * MEDICO; todo lo demás bajo "/api/auth/usuarios/**" sigue siendo solo ADMIN.
 */
@RestController
@RequestMapping("/api/auth/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final IUsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(@RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(
            @PathVariable String id,
            @RequestBody @Valid UsuarioUpdateRequest request) {
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtener(@PathVariable String id) {
        return ResponseEntity.ok(usuarioService.obtener(id));
    }

    /**
     * Listado de usuarios. Abierto también a MEDICO (de solo lectura) porque
     * lo usa el selector de "referir paciente a otro médico" en el frontend.
     * Crear/editar/eliminar usuarios sigue siendo exclusivo de ADMIN.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar(
            @RequestParam(required = false) Rol rol) {
        List<UsuarioResponse> resultado = (rol != null)
                ? usuarioService.listarPorRol(rol)
                : usuarioService.listar();
        return ResponseEntity.ok(resultado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
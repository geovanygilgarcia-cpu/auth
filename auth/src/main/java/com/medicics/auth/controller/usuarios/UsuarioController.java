package com.medicics.auth.controller.usuarios;


import com.medicics.auth.dto.usuarios.request.UsuarioRequest;
import com.medicics.auth.dto.usuarios.request.UsuarioUpdateRequest;
import com.medicics.auth.dto.usuarios.response.UsuarioResponse;
import com.medicics.auth.model.Rol;
import com.medicics.auth.service.usuarios.IUsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Todo este controller ya está protegido a nivel de SecurityConfig
 * (requestMatchers("/api/auth/usuarios/**").hasRole("ADMIN")), y además
 * reforzamos con @PreAuthorize por claridad método a método.
 */
@RestController
@RequestMapping("/api/auth/usuarios")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(@RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(
            @PathVariable String id,
            @RequestBody @Valid UsuarioUpdateRequest request) {
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtener(@PathVariable String id) {
        return ResponseEntity.ok(usuarioService.obtener(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar(
            @RequestParam(required = false) Rol rol) {
        List<UsuarioResponse> resultado = (rol != null)
                ? usuarioService.listarPorRol(rol)
                : usuarioService.listar();
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

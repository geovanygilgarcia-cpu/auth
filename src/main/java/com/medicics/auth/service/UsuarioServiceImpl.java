package com.medicics.auth.service;

import com.medicics.auth.dto.request.UsuarioRequest;
import com.medicics.auth.dto.request.UsuarioUpdateRequest;
import com.medicics.auth.dto.response.UsuarioResponse;
import com.medicics.auth.exception.EmailYaRegistradoException;
import com.medicics.auth.exception.UsuarioNoEncontradoException;
import com.medicics.auth.model.Rol;
import com.medicics.auth.model.Usuario;
import com.medicics.auth.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UsuarioResponse crear(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new EmailYaRegistradoException(request.email());
        }

        Usuario usuario = Usuario.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .nombreCompleto(request.nombreCompleto())
                .cedulaProfesional(request.cedulaProfesional())
                .especialidad(request.especialidad())
                .subespecialidad(request.subespecialidad())
                .rol(request.rol())
                .activo(true)
                .build();

        return UsuarioResponse.from(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public UsuarioResponse actualizar(String id, UsuarioUpdateRequest request) {
        Usuario usuario = buscarPorIdOLanzar(id);

        // Si cambia el correo, valida que el nuevo no esté en uso por otro usuario.
        if (!usuario.getEmail().equalsIgnoreCase(request.email())
                && usuarioRepository.existsByEmail(request.email())) {
            throw new EmailYaRegistradoException(request.email());
        }

        usuario.setEmail(request.email());
        usuario.setNombreCompleto(request.nombreCompleto());
        usuario.setCedulaProfesional(request.cedulaProfesional());
        usuario.setEspecialidad(request.especialidad());
        usuario.setSubespecialidad(request.subespecialidad());
        usuario.setRol(request.rol());
        usuario.setActivo(request.activo());

        // Solo se toca la contraseña si mandaron una nueva.
        if (request.password() != null && !request.password().isBlank()) {
            usuario.setPasswordHash(passwordEncoder.encode(request.password()));
        }

        return UsuarioResponse.from(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioResponse obtener(String id) {
        return UsuarioResponse.from(buscarPorIdOLanzar(id));
    }

    @Override
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponse::from)
                .toList();
    }

    @Override
    public List<UsuarioResponse> listarPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol).stream()
                .map(UsuarioResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public void eliminar(String id) {
        Usuario usuario = buscarPorIdOLanzar(id);
        // Baja lógica en vez de DELETE físico: si el doctor ya tiene recetas o
        // historias clínicas asociadas en otros microservicios, borrarlo de
        // verdad rompería esas referencias. Con "activo = false" ya no puede
        // iniciar sesión, pero su historial no se pierde.
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    private Usuario buscarPorIdOLanzar(String id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(id));
    }
}

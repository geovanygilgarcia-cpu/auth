package com.medicics.auth.service;

import com.medicics.auth.dto.request.LoginRequest;
import com.medicics.auth.dto.response.LoginResponse;
import com.medicics.auth.model.Usuario;
import com.medicics.auth.repository.UsuarioRepository;
import com.medicics.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {
        // Si el correo o la contraseña no son válidos, esto lanza
        // BadCredentialsException automáticamente (lo captura el
        // GlobalExceptionHandler y responde 401).
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String token = jwtService.generateToken(usuario);

        String sexo = usuario.getSexo() != null ? usuario.getSexo().name() : null;

        return new LoginResponse(
                usuario.getId(),
                token,
                usuario.getRol().name(),
                usuario.getNombreCompleto(),
                sexo,
                usuario.getEspecialidad(),
                usuario.getSubespecialidad()
        );
    }
}
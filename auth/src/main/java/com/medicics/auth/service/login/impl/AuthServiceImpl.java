package com.medicics.auth.service.login.impl;

import com.medicics.auth.dto.login.request.LoginRequest;
import com.medicics.auth.dto.login.response.LoginResponse;
import com.medicics.auth.model.Usuario;
import com.medicics.auth.repository.UsuarioRepository;
import com.medicics.auth.security.JwtService;
import com.medicics.auth.service.login.IAuthService;
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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String token = jwtService.generateToken(usuario);

        return new LoginResponse(token, usuario.getRol().name(), usuario.getNombreCompleto());
    }
}

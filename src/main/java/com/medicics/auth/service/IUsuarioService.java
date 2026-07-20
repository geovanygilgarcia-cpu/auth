package com.medicics.auth.service;

import com.medicics.auth.dto.request.UsuarioRequest;
import com.medicics.auth.dto.request.UsuarioUpdateRequest;
import com.medicics.auth.dto.response.UsuarioResponse;
import com.medicics.auth.model.Rol;

import java.util.List;

public interface IUsuarioService {

    UsuarioResponse crear(UsuarioRequest request);

    UsuarioResponse actualizar(String id, UsuarioUpdateRequest request);

    UsuarioResponse obtener(String id);

    List<UsuarioResponse> listar();

    List<UsuarioResponse> listarPorRol(Rol rol);

    void eliminar(String id);
}

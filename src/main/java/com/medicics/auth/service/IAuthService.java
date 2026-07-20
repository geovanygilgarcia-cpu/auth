package com.medicics.auth.service;

import com.medicics.auth.dto.request.LoginRequest;
import com.medicics.auth.dto.response.LoginResponse;

public interface IAuthService {

    LoginResponse login(LoginRequest request);
}

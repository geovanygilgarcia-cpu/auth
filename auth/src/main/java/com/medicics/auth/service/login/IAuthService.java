package com.medicics.auth.service.login;

import com.medicics.auth.dto.login.request.LoginRequest;
import com.medicics.auth.dto.login.response.LoginResponse;

public interface IAuthService {

    LoginResponse login(LoginRequest request);
}

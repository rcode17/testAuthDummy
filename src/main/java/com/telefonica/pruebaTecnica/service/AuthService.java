package com.telefonica.pruebaTecnica.service;

import com.telefonica.pruebaTecnica.dto.LoginDTO;
import com.telefonica.pruebaTecnica.dto.ResponseUserInfoDTO;

public interface AuthService {
	ResponseUserInfoDTO login(LoginDTO loginRequest);
}

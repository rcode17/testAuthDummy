package com.telefonica.pruebaTecnica.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telefonica.pruebaTecnica.dto.LoginDTO;
import com.telefonica.pruebaTecnica.dto.ResponseUserInfoDTO;
import com.telefonica.pruebaTecnica.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class ControllerAuth {
	
	private final AuthService authService;
    public ControllerAuth(AuthService authService) {
        this.authService = authService;
    }
	
	@PostMapping("/login")
    public ResponseEntity<ResponseUserInfoDTO> login(@RequestBody LoginDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

}

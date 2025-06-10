package com.telefonica.pruebaTecnica.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telefonica.pruebaTecnica.clientFeign.DummyClient;
import com.telefonica.pruebaTecnica.dto.LoginDTO;
import com.telefonica.pruebaTecnica.dto.ResponseUserInfoDTO;
import com.telefonica.pruebaTecnica.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class ControllerAuth {
	
	private final AuthService authService;
    private final DummyClient dummyClient;

    public ControllerAuth(AuthService authService, DummyClient dummyClient) {
        this.authService = authService;
        this.dummyClient = dummyClient;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseUserInfoDTO> login(@RequestBody LoginDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/dummy-users")
    public ResponseEntity<?> obtenerUsuariosDummy() {
        return dummyClient.getAllUsers();
    }

}

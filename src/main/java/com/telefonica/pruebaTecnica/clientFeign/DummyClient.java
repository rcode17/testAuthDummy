package com.telefonica.pruebaTecnica.clientFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.telefonica.pruebaTecnica.config.Config;
import com.telefonica.pruebaTecnica.dto.LoginDTO;
import com.telefonica.pruebaTecnica.dto.ResponseLoginDTO;
import com.telefonica.pruebaTecnica.dto.ResponseUserInfoDTO;

@FeignClient(name = "dummyClient", url = "https://dummyjson.com", configuration = Config.class)
public interface DummyClient {

    @PostMapping("/auth/login")
    ResponseEntity<ResponseLoginDTO> login(@RequestBody LoginDTO request);

    @GetMapping("/auth/me")
    ResponseEntity<ResponseUserInfoDTO> getUserInfo(@RequestHeader("Cookie") String token);
    
    @GetMapping("/users")
    ResponseEntity<Object> getAllUsers();
}

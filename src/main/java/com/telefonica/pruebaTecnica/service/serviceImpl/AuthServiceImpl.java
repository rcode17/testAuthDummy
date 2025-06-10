package com.telefonica.pruebaTecnica.service.serviceImpl;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.telefonica.pruebaTecnica.clientFeign.DummyClient;
import com.telefonica.pruebaTecnica.dto.LoginDTO;
import com.telefonica.pruebaTecnica.dto.ResponseLoginDTO;
import com.telefonica.pruebaTecnica.dto.ResponseUserInfoDTO;
import com.telefonica.pruebaTecnica.model.LoginLog;
import com.telefonica.pruebaTecnica.repository.LoginLogRepository;
import com.telefonica.pruebaTecnica.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final DummyClient dummyClient;
    private final LoginLogRepository logRepository;

    public AuthServiceImpl(DummyClient dummyClient, LoginLogRepository logRepository) {
        this.dummyClient = dummyClient;
        this.logRepository = logRepository;
    }

    @Override
    public ResponseUserInfoDTO login(LoginDTO loginRequest) {
        
        ResponseEntity<ResponseLoginDTO> response = dummyClient.login(loginRequest);

        String cookieHeader = response.getHeaders().getFirst("Set-Cookie");
        String accessToken = (cookieHeader != null) ? cookieHeader.split(";")[0] : null;

        if (accessToken == null || response.getBody() == null) {
            throw new RuntimeException("Login fallido: token no encontrado");
        }

        ResponseEntity<ResponseUserInfoDTO> userResponse = dummyClient.getUserInfo(accessToken);
        ResponseUserInfoDTO user = userResponse.getBody();

        if (user != null) {
            LoginLog log = new LoginLog();
            log.setUsername(user.getUsername());
            log.setLoginTime(LocalDateTime.now());
            log.setAccessToken(accessToken); // Usamos el token real
            log.setRefreshToken(response.getBody().getRefreshToken());

            logRepository.save(log);
        }

        return user;
    }
}

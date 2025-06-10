package com.telefonica.pruebaTecnica;

import com.telefonica.pruebaTecnica.clientFeign.DummyClient;
import com.telefonica.pruebaTecnica.dto.LoginDTO;
import com.telefonica.pruebaTecnica.dto.ResponseLoginDTO;
import com.telefonica.pruebaTecnica.dto.ResponseUserInfoDTO;
import com.telefonica.pruebaTecnica.model.LoginLog;
import com.telefonica.pruebaTecnica.repository.LoginLogRepository;
import com.telefonica.pruebaTecnica.service.AuthService;
import com.telefonica.pruebaTecnica.service.serviceImpl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    private DummyClient dummyClient;
    private LoginLogRepository loginLogRepository;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        dummyClient = Mockito.mock(DummyClient.class);
        loginLogRepository = Mockito.mock(LoginLogRepository.class);
        authService = new AuthServiceImpl(dummyClient, loginLogRepository);
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        LoginDTO login = new LoginDTO("emilysj", "emilyspass");

        ResponseLoginDTO loginResponse = new ResponseLoginDTO();
        loginResponse.setToken("token123");
        loginResponse.setRefreshToken("refresh123");

        ResponseUserInfoDTO userInfo = new ResponseUserInfoDTO();
        userInfo.setUsername("emilys");
        userInfo.setEmail("emily@example.com");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "auth_token_cookie");

        Mockito.when(dummyClient.login(login)).thenReturn(ResponseEntity.ok().headers(headers).body(loginResponse));
        Mockito.when(dummyClient.getUserInfo("auth_token_cookie")).thenReturn(ResponseEntity.ok(userInfo));
        LoginLog logSaved = new LoginLog();
        Mockito.when(loginLogRepository.save(Mockito.any(LoginLog.class)))
               .thenReturn(logSaved);


        // Act
        ResponseUserInfoDTO result = authService.login(login);

        // Assert
        assertNotNull(result);
        assertEquals("emilys", result.getUsername());
        assertEquals("emily@example.com", result.getEmail());
    }
}

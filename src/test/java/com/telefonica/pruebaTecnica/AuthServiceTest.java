package com.telefonica.pruebaTecnica;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.telefonica.pruebaTecnica.clientFeign.DummyClient;
import com.telefonica.pruebaTecnica.dto.LoginDTO;
import com.telefonica.pruebaTecnica.dto.ResponseLoginDTO;
import com.telefonica.pruebaTecnica.dto.ResponseUserInfoDTO;
import com.telefonica.pruebaTecnica.repository.LoginLogRepository;
import com.telefonica.pruebaTecnica.service.AuthService;

public class AuthServiceTest {
	@MockBean
    private DummyClient dummyClient;

    @MockBean
    private LoginLogRepository logRepository;

    @Autowired
    private AuthService authService;

    @Test
    void testLoginSuccess() {
        LoginDTO login = new LoginDTO("emilys", "emilyspass");

        ResponseLoginDTO loginResponse = new ResponseLoginDTO();
        loginResponse.setToken("token123");
        loginResponse.setRefreshToken("refresh123");

        ResponseUserInfoDTO userInfo = new ResponseUserInfoDTO();
        userInfo.setUsername("emilys");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "auth_token_cookie");

        Mockito.when(dummyClient.login(login)).thenReturn(ResponseEntity.ok().headers(headers).body(loginResponse));
        Mockito.when(dummyClient.getUserInfo("auth_token_cookie")).thenReturn(ResponseEntity.ok(userInfo));

        ResponseUserInfoDTO result = authService.login(login);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("emilys", result.getUsername());
    }
}
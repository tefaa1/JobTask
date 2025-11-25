package com.example.JobTask.service;

import com.example.JobTask.dto.user.AuthenticationResponseDTO;
import com.example.JobTask.dto.user.LoginRequestDTO;
import com.example.JobTask.dto.user.RegisterRequestDTO;

public interface AuthService {

    AuthenticationResponseDTO login(LoginRequestDTO loginRequestDTO);

    void register(RegisterRequestDTO registerRequestDTO);

    String refresh(String refreshToken);
}

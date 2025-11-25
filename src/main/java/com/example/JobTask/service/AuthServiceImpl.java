package com.example.JobTask.service;

import com.example.JobTask.Repository.UserRepository;
import com.example.JobTask.dto.user.AuthenticationResponseDTO;
import com.example.JobTask.dto.user.LoginRequestDTO;
import com.example.JobTask.dto.user.RegisterRequestDTO;
import com.example.JobTask.entity.User;
import com.example.JobTask.mapper.UserMapper;
import com.example.JobTask.security.JWTUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO loginRequestDTO) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(),loginRequestDTO.getPassword());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String accessToken = jwtUtil.generateAccessToken(loginRequestDTO.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(loginRequestDTO.getEmail());

        return AuthenticationResponseDTO
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public void register(RegisterRequestDTO registerRequestDTO) {

        if(userRepository.existsByEmail(registerRequestDTO.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use");
        }

        User user = userMapper.toEntity(registerRequestDTO);
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public String refresh(String refreshToken) {

        if(!jwtUtil.isTokenValid(refreshToken)){
            throw new JwtException("Token expired or invalid");
        }

        if(jwtUtil.isAccessToken(refreshToken)){
            throw new JwtException("Access token is not allowed here");
        }

        String email = jwtUtil.extractEmail(refreshToken);

        return jwtUtil.generateAccessToken(email);
    }
}

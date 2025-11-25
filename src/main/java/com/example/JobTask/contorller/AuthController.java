package com.example.JobTask.contorller;

import com.example.JobTask.dto.user.AuthenticationResponseDTO;
import com.example.JobTask.dto.user.LoginRequestDTO;
import com.example.JobTask.dto.user.RegisterRequestDTO;
import com.example.JobTask.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public ResponseEntity<?>login(@Valid @RequestBody LoginRequestDTO loginDTO,
                                     HttpServletResponse response){

        AuthenticationResponseDTO authenticationResponseDTO = authService.login(loginDTO);

        response.setHeader("Authorization","Bearer "+authenticationResponseDTO.getAccessToken());

        Cookie refreshTokencookie = new Cookie("refreshToken",authenticationResponseDTO.getRefreshToken());

        refreshTokencookie.setHttpOnly(true);
        refreshTokencookie.setPath("/");
        refreshTokencookie.setMaxAge(7*24*60*60);

        response.addCookie(refreshTokencookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?>register(@Valid @RequestBody RegisterRequestDTO requestDTO){

        authService.register(requestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?>refresh(HttpServletRequest request,
                                    HttpServletResponse response){

        Cookie[]cookies = request.getCookies();

        String refreshToken = null;

        for(Cookie cookie: cookies){

            if("refreshToken".equals(cookie.getName())){
                refreshToken = cookie.getValue();
                break;
            }
        }

        if(refreshToken == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String newAccessToken = authService.refresh(refreshToken);

        response.setHeader("Authorization","Bearer "+newAccessToken);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok().build();
    }
}

package com.blog.controller;


import com.blog.config.AppConfig;
import com.blog.domain.AppUser;
import com.blog.exception.InvalidRequest;
import com.blog.exception.InvalidSigninInformation;
import com.blog.repository.UserRepository;

import com.blog.request.Signup;

import com.blog.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;
//    @PostMapping("/auth/login")
//    public ResponseEntity<Object> login(@RequestBody Login login){
//        String accessToken = authService.signin(login);
//        ResponseCookie cookie = ResponseCookie.from("SESSIONs", accessToken)
//                .domain("localhost") // todo 서버 환경에 따른 분리 필요
//                .path("/")
//                .httpOnly(true)
//                .secure(false)
//                .maxAge(Duration.ofDays(30))
//                .sameSite("Strict")
//                .build();
//        // 토큰을 응답
//        log.info(">>>>>>>>>>>>>>> cookie = {} ", cookie.toString());
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.SET_COOKIE, cookie.toString())
//                .build();
//
//    }
//    @PostMapping("/auth/login")
//    public SessionResponse login(@RequestBody Login login) {
//        Long userId = authService.signin(login);
//
//        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());
//
//        String jws = Jwts.builder().setSubject(String.valueOf(userId))
//                .signWith(key)
//                .setIssuedAt(new Date())
//                .compact();
//
//        return new SessionResponse(jws);
//    }

//    @GetMapping("/auth/login")
//    public String login(){
//        return "로그인 페이지";
//    }


    @RequestMapping("favicon.ico")
    public void favicon() {
        // 빈 응답으로 처리하거나 리소스를 반환하지 않음
    }


    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup signup){
        authService.signup(signup);
    }

}

package com.blog.controller;

import com.blog.config.UserPrincipal;
import com.blog.response.UserResponse;
import com.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users/me")
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        if(userPrincipal == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserResponse userProfile = userService.getUserProfile(userPrincipal.getUserId());

        return ResponseEntity.ok().body(userProfile);
    }
}

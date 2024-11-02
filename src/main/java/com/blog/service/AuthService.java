package com.blog.service;


import com.blog.crypto.PasswordEncoders;
import com.blog.domain.AppUser;
import com.blog.exception.AlreadyExistEmailException;
import com.blog.exception.InvalidSigninInformation;
import com.blog.repository.UserRepository;

import com.blog.request.Signup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public void signup(Signup signup) {
        Optional<AppUser> findUser = userRepository.findByEmail(signup.getEmail());
        if(findUser.isPresent()) {
            throw  new AlreadyExistEmailException();
        }

        String encryptedPassword = passwordEncoder.encode(signup.getPassword());
        AppUser user = AppUser.builder()
                .name(signup.getName())
                .password(encryptedPassword)
                .email(signup.getEmail())
                .build();
        userRepository.save(user);
    }
}

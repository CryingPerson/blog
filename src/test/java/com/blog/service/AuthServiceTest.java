package com.blog.service;


import com.blog.domain.AppUser;
import com.blog.exception.AlreadyExistEmailException;
import com.blog.exception.InvalidSigninInformation;
import com.blog.repository.UserRepository;

import com.blog.request.Signup;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        // given
        Signup signup = Signup.builder()
                .password("1111")
                .email("leee@naber.com")
                .name("leee")
                .build();
        // when
        authService.signup(signup);
        // then
        assertEquals(1, userRepository.count());
        AppUser user = userRepository.findAll().iterator().next();
        assertEquals("leee@naber.com", user.getEmail());
        assertEquals("leee", user.getName());
    }

    @Test
    @DisplayName("회원가입시 중복이메일")
    void test2() {
        //


        AppUser user = AppUser.builder()
                .name("lese")
                .password("1111")
                .email("leee@naber.com")
                .build();
        userRepository.save(user);
        // given
        Signup signup = Signup.builder()
                .password("1111")
                .email("leee@naber.com")
                .name("leee")
                .build();
        // when
        Assertions.assertThrows(AlreadyExistEmailException.class,
                () -> authService.signup(signup));

        // then
    }

}
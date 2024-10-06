package com.blog.controller;

import com.blog.domain.AppUser;
import com.blog.repository.UserRepository;
import com.blog.request.Signup;
import com.blog.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void clean(){
        userRepository.deleteAll();
        System.out.println("User count after clean: " + userRepository.count());
    }


    @Test
    @DisplayName("회원가입.")
    void test7() throws Exception{


        Signup signup = Signup.builder()
                .name("이시현")
                .password("1111")
                .email("opnice12@naver.com")
                .build();


        mvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signup))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        AppUser user = userRepository.findAll().iterator().next();
        assertEquals("이시현", user.getName());
        assertNotEquals("1111", user.getPassword());
        assertTrue(passwordEncoder.matches("1111", user.getPassword()));
        System.out.println(user.getPassword());
    }


//    @Test
//    @WithMockUser(username = "opnice12@naver.com",
//            roles = {"ADMIN"},
//            password ="1111" )
//    public void testLogin() throws Exception {
//        Signup signup = Signup.builder()
//                .name("이시현")
//                .password("1111")
//                .email("opnice12@naver.com")
//                .build();
//
//        mvc.perform(post("/auth/signup")
//                        .content(objectMapper.writeValueAsString(signup))
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//
//        mvc.perform(post("/auth/login")
//                        .param("username", "opnice12@naver.com")
//                        .param("password", "1111"))
//                .andExpect(status().is3xxRedirection());// 리다이렉트 URL 확인 (변경 필요)
//    }
}
package com.blog.controller;

import com.blog.annotation.BlogMockUser;
import com.blog.domain.AppUser;
import com.blog.domain.Post;
import com.blog.repository.post.PostRepository;
import com.blog.repository.UserRepository;
import com.blog.request.post.PostCreate;
import com.blog.request.post.PostEdit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    private static final Logger log = LoggerFactory.getLogger(PostControllerTest.class);
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;
    @AfterEach
        // 각테스트 실행 전에
    void claen() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("글 작성 요청시 title 값은 필수다.")
    void test2() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();
        String json = objectMapper.writeValueAsString(request);
        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청 입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))

                .andDo(print());
    }

//    @Test
//    @DisplayName("글 작성 요청시 DB에 값이 저장된다.")
//    void test3() throws Exception {
//        // given
//        PostCreate request = PostCreate.builder()
//                .title("제목입니다.")
//                .content("내용입니다.")
//                .build();
//
//        String json = objectMapper.writeValueAsString(request);
//
//        // when
//        mockMvc.perform(post("/posts")
//                        .contentType(APPLICATION_JSON)
//                        .content(json)
//                ) // application/json
//                .andExpect(status().isOk())
//                .andDo(print());
//        // then
//        assertEquals(1L, postRepository.count());
//
//        Post post = postRepository.findAll().get(0);
//        assertEquals("제목입니다.", post.getTitle());
//        assertEquals("내용입니다.", post.getContent());
//    }
    @Test
    @BlogMockUser()
    @DisplayName("게시글 작성")
    void test3() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                ) // application/json
                .andExpect(status().isOk())
                .andDo(print());
        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        // given
        AppUser user = AppUser.builder()
                .name("이시현")
                .password("1111")
                .email("opnice12@naver.com")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .user(user)
                .build();
        postRepository.save(post);
        // expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                ) // application/json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());

    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {
        // given
        AppUser user = AppUser.builder()
                .name("이시현")
                .password("1111")
                .email("opnice12@naver.com")
                .build();

        userRepository.save(user);
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("이시현 제목 " + i)
                        .content("반포자이 " + i)
                        .user(user)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);
        // expected
        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON)
                ) // application/json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("이시현 제목 19"))
                .andExpect(jsonPath("$[0].content").value("반포자이 19"))
                .andDo(print());

    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test6() throws Exception {
        // given
        AppUser user = AppUser.builder()
                .name("이시현")
                .password("1111")
                .email("opnice12@naver.com")
                .build();

        userRepository.save(user);
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("이시현 제목 " + i)
                        .content("반포자이 " + i)
                        .user(user)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);
        // expected
        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON)
                ) // application/json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("이시현 제목 19"))
                .andExpect(jsonPath("$[0].content").value("반포자이 19"))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "opnice12@naver.com",
            roles = {"ADMIN"},
            password ="1111" )
    @DisplayName("글 제목 수정")
    void test7() throws Exception {
        // given
        AppUser user = userRepository.findAll().get(0);
        Post post = Post.builder()
                .title("이시현")
                .content("반포자이")
                .user(user)
                .build();


        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("치킨사랑해")
                .content("치킨집")
                .build();

        // expected
        mockMvc.perform(put("/posts/{postId}", post.getId()) // PATCH /posts/{postId}
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                ) // application/json
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @BlogMockUser
    @DisplayName("게시글 삭제")
    void test8() throws Exception {

        AppUser user = userRepository.findAll().get(0);


        Post post = Post.builder()
                .title("이시현")
                .content("반포자이")
                .user(user)
                .build();


        postRepository.save(post);

        // expected
        mockMvc.perform(delete("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void test9() throws Exception {
        mockMvc.perform(get("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "opnice12@naver.com",
            roles = {"ADMIN"},
            password ="1111" )
    @DisplayName("존재하지 않는 게시글 수정")
    void test10() throws Exception {

        PostEdit postEdit = PostEdit.builder()
                .title("이시현")
                .content("반포자이")
                .build();

        mockMvc.perform(put("/posts/{postId}", 1L) // PATCH /posts/{postId}
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                ) // application/json
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성시 제목에 '바보' 는 포함 될수 없다.")
    void test11() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title("나는 바보임 ㅎㅎ.")
                .content("반포자이.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                ) // application/json
                .andExpect(status().isBadRequest())
                .andDo(print());
        // then
    }
}

//API 문서 생성

// GET /posts/{postId} -> 단건 조회
// POST /posts -> 게시글 등록

// 클라이언트 입장 어떤 API 있는지 모름

// Spring RestDocs
// - 운영코도에 -> 영향
// - 코드 수정 -> 문서를 수정 X -> 코드(기능) <-> 문서
// - Test 케이스 실행 -> 문서를 생성해준다.
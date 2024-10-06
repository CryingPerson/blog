package com.blog.controller;

import com.blog.annotation.BlogMockUser;
import com.blog.domain.AppUser;
import com.blog.domain.Comment;
import com.blog.domain.Post;
import com.blog.repository.UserRepository;
import com.blog.repository.comment.CommentRepository;
import com.blog.repository.post.PostRepository;
import com.blog.request.comment.CommentCreate;
import com.blog.request.comment.CommentDelete;
import com.blog.request.post.PostCreate;
import com.blog.request.post.PostEdit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CommentControllerTest {

    private static final Logger log = LoggerFactory.getLogger(CommentControllerTest.class);
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
        // 각테스트 실행 전에
    void claen() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @BlogMockUser
    @DisplayName("댓글 작성")
    void test1() throws Exception {
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

        CommentCreate commentCreate = CommentCreate.builder()
                        .author("이시현이다 ㅋ")
                                .password("123465")
                                        .content("댓글이다. 아아아아아아아아아아아아")
                                                .build();

        String json = objectMapper.writeValueAsString(commentCreate);

        // when
        mockMvc.perform(post("/posts/{postId}/comments", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());

                //then

        Assertions.assertEquals(1L, commentRepository.count());
        Comment saveComment = commentRepository.findAll().get(0);
        assertEquals("이시현이다 ㅋ", saveComment.getAuthor());
        assertNotEquals("123465", saveComment.getPassword());
        assertTrue(passwordEncoder.matches("123465", saveComment.getPassword()));
        assertEquals("댓글이다. 아아아아아아아아아아아아", saveComment.getContent());
    }

    @Test
    @DisplayName("댓글 삭제")
    void test2() throws Exception {
        // given
        AppUser user = AppUser.builder()
                .name("호돌맨")
                .email("hodolman88@gmail.com")
                .password("123456")
                .build();
        userRepository.save(user);

        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .user(user)
                .build();
        postRepository.save(post);

        String encryptedPassword = passwordEncoder.encode("123456");
        Comment comment = Comment.builder()
                .author("호돌순")
                .password(encryptedPassword)
                .content("으하하하하하하하하 10글자 제한 빡치네")
                .build();
        comment.setPost(post);
        commentRepository.save(comment);

        CommentDelete request = new CommentDelete("123456");
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/comments/{commentId}/delete", comment.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
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
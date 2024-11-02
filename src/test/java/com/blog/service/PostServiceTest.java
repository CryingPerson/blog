package com.blog.service;

import com.blog.domain.AppUser;
import com.blog.domain.Post;
import com.blog.exception.PostNotFound;
import com.blog.repository.post.PostRepository;
import com.blog.repository.UserRepository;
import com.blog.request.post.PostCreate;
import com.blog.request.post.PostEdit;
import com.blog.request.post.PostSearch;
import com.blog.response.PagingResponse;
import com.blog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        AppUser user = AppUser.builder()
                .name("이시현")
                .email("oo@naver.com")
                .password("1111")
                .build();

        userRepository.save(user);
        PostCreate postCreate = PostCreate.builder()

                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        postService.write(user.getId(), postCreate);

        //then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given
        Post reqeustPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(reqeustPost);

        // when
        PostResponse response = postService.get(reqeustPost.getId());

        // then

        assertNotNull(response);

        assertEquals(1L, postRepository.count());
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("이시현 제목 " + i)
                        .content("반포자이 " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .size(10)
                .build();
        // when
        PagingResponse<PostResponse> posts = postService.getList(postSearch);

        // then


        // then


    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
        // given
        Post post = Post.builder()
                .title("이시현")
                .content("반포자이")
                .build();


        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("치킨사랑해")
                .content("반포자이")
                .build();

        // when
        postService.edit(post.getId(), postEdit);
        // then
        Post chabgePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        Assertions.assertEquals("치킨사랑해",chabgePost.getTitle());

    }
    @Test
    @DisplayName("글 내용 수정")
    void test5() {
        // given
        Post post = Post.builder()
                .title("이시현")
                .content("반포자이")
                .build();


        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("치킨집")
                .build();
        // sql -> select, limit, offset

        // when
        postService.edit(post.getId(), postEdit);
        // then
        Post chabgePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        Assertions.assertEquals("치킨집",chabgePost.getContent());

    }
    @Test
    @DisplayName("글 내용 수정")
    void test6() {
        // given
        Post post = Post.builder()
                .title("이시현")
                .content("반포자이")
                .build();


        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("치킨집")
                .build();
        // sql -> select, limit, offset

        // when
        postService.edit(post.getId(), postEdit);
        // then
        Post chabgePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        assertEquals("이시현",chabgePost.getTitle());
        Assertions.assertEquals("치킨집",chabgePost.getContent());


    }

    @Test
    @DisplayName("게시글 삭제")
    void test7(){
        // given
        Post post = Post.builder()
                .title("이시현")
                .content("반포자이")
                .build();


        postRepository.save(post);

        // when
        postService.delete(post.getId());

        // then

        assertEquals(0, postRepository.count());
    }
    @Test
    @DisplayName("글 1개 조회 - 존재 하지 않는 글")
    void test8() {
        // given
        Post post = Post.builder()
                .title("시현잉")
                .content("반포자이")
                .build();
        postRepository.save(post);

        // post.getId() // primary_id = 1

        // expected

         assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test9(){
        // given
        Post post = Post.builder()
                .title("이시현")
                .content("반포자이")
                .build();


        postRepository.save(post);
        // expected

        assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 내용 수정 - 존재 하지 않는 게시글 수정")
    void test10() {
        // given
        Post post = Post.builder()
                .title("이시현")
                .content("반포자이")
                .build();


        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("치킨집")
                .build();
        // sql -> select, limit, offset

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId() + 1L, postEdit);
        });

    }
}
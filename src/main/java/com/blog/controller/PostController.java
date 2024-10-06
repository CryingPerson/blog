package com.blog.controller;


import com.blog.config.UserPrincipal;
import com.blog.request.post.PostCreate;
import com.blog.request.post.PostEdit;
import com.blog.request.post.PostSearch;
import com.blog.response.PostResponse;
import com.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;


    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록, 글 단건 조회, 글 리스트 조회
    // CRUD -> Create, Read, Update
    // POST Method

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/posts")
    public void post(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid PostCreate request) {
        // 1. GET parameter
        // 2. POST(body) value
        // 3. Header
        request.validate();
        postService.write(userPrincipal.getUserId(), request);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId){
        // Request(DTO) 클래스
        // Response 클래스
        // 응답 클래스 분리하세요(서비스 정책에 맞는)
        return postService.get(postId);
    }
    /**
     *  /posts -> 글 전체 조회(검색 + 페이징)
     *  /posts/{pstId} -> 글 한개만 조회
     */
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request){
        postService.edit(postId, request);
    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_ADMIN') && hasPermission(#postId, 'POST', 'DELETE')")
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId){
        postService.delete(postId);
    }
}

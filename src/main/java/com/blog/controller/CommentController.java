package com.blog.controller;

import com.blog.request.comment.CommentCreate;
import com.blog.request.comment.CommentDelete;
import com.blog.response.NotificationDTO;
import com.blog.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public NotificationDTO write(@PathVariable Long postId, @RequestBody @Valid CommentCreate commentCreate) {
      return   commentService.write(postId, commentCreate);
    }

    @PostMapping("/comments/{commentId}/delete")
    public void delete(@PathVariable Long commentId, @RequestBody @Valid CommentDelete request) {
        commentService.delete(commentId, request);
    }
}

package com.blog.service;

import com.blog.domain.Comment;
import com.blog.domain.Post;
import com.blog.exception.CommentNotFound;
import com.blog.exception.InvalidPassword;
import com.blog.exception.PostNotFound;
import com.blog.repository.comment.CommentRepository;
import com.blog.repository.post.PostRepository;
import com.blog.request.comment.CommentCreate;
import com.blog.request.comment.CommentDelete;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void write(Long postId, CommentCreate commentCreate) {
        String eyPassword = passwordEncoder.encode(commentCreate.getPassword());

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        Comment comment = Comment.builder()
                .author(commentCreate.getAuthor())
                .password(eyPassword)
                .content(commentCreate.getContent())
                .build();

        post.addComment(comment);
    }

    public void delete(Long commentId, CommentDelete request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);

        String encryptedPassword = comment.getPassword();
        if(!passwordEncoder.matches(request.getPassword(), encryptedPassword)){
            throw new InvalidPassword();
        }
        commentRepository.delete(comment);
    }
}

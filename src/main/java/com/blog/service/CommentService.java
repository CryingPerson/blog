package com.blog.service;

import com.blog.domain.Comment;
import com.blog.domain.Post;
import com.blog.exception.CommentNotFound;
import com.blog.exception.InvalidPassword;
import com.blog.exception.PostNotFound;
import com.blog.repository.UserRepository;
import com.blog.repository.comment.CommentRepository;
import com.blog.repository.post.PostRepository;
import com.blog.request.comment.CommentCreate;
import com.blog.request.comment.CommentDelete;
import com.blog.response.NotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final NotificationService notificationService;


    @Transactional
    public NotificationDTO write(Long postId, CommentCreate commentCreate) {
        String eyPassword = passwordEncoder.encode(commentCreate.getPassword());

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        Comment comment = Comment.builder()
                .author(commentCreate.getAuthor())
                .password(eyPassword)
                .content(commentCreate.getContent())
                .build();
        comment.setUser(userRepository.findById(post.getUser().getId()).get());

        post.addComment(comment);
        commentRepository.save(comment);
        NotificationDTO notificationDTO = new NotificationDTO(
                "새로운 댓글이 등록되었습니다!",
                postId,
                comment.getId()
        );
        notificationService.notify(post.getUserId(), notificationDTO);

        return notificationDTO;
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

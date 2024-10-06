package com.blog.config;

import com.blog.domain.Post;
import com.blog.exception.PostNotFound;
import com.blog.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@RequiredArgsConstructor
@Slf4j
public class BlogPermissionEvaluator implements PermissionEvaluator {

    private final PostRepository postRepository;
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        UserPrincipal principal = (UserPrincipal)authentication.getPrincipal();

        Post post = postRepository.findById((Long) targetId)
                .orElseThrow(PostNotFound::new);

        if(!post.getUserId().equals(principal.getUserId())) {
            log.error("User {} not authorized to post {}", principal.getUserId(), post.getId());
            return false;
        }
        return true;
    }
}

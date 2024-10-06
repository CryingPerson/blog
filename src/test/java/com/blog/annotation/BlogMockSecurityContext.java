package com.blog.annotation;

import com.blog.config.UserPrincipal;
import com.blog.domain.AppUser;
import com.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;


@RequiredArgsConstructor
public class BlogMockSecurityContext implements WithSecurityContextFactory<BlogMockUser> {

    private final UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(BlogMockUser annotation) {
        var user = AppUser.builder()
                .email(annotation.email())
                .password(annotation.password())
                .name(annotation.name())
                .build();

        userRepository.save(user);

        var principal = new UserPrincipal(user);
        var role = new SimpleGrantedAuthority("ROLE_ADMIN");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(principal, user.getPassword()
                , List.of(role));

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(usernamePasswordAuthenticationToken);

        return context;
    }

}

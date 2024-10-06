package com.blog.config;

import com.blog.domain.AppUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

    private final Long userId;

    // role: 역할 -> 관리자, 사용자, 매니저
    // authority : 권한, -> 글쓰기, 글 읽기, 사용자 정지 시키기

    public UserPrincipal(AppUser user){
        super(user.getEmail(), user.getPassword(),
                List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN")
                ));// ROLE_ 을 붙여줘야 역할임 그게없으면 권한임
        this.userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }
}

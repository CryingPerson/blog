package com.blog.service;

import com.blog.domain.AppUser;
import com.blog.exception.UserNotFound;
import com.blog.repository.UserRepository;
import com.blog.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUserProfile(Long userId){
        AppUser user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        return new UserResponse(user);
    }
}

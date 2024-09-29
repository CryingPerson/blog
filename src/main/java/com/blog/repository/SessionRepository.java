package com.blog.repository;



import com.blog.domain.AppUser;


import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByEmailAndPassword(String email, String password);
}

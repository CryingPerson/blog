package com.blog.repository;



import com.blog.domain.AppUser;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmailAndPassword(String email, String password);
    Optional<AppUser> findByEmail(String email);
}

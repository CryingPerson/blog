package com.blog.crypto;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ScryptPasswordEncoder {

   private static final PasswordEncoder encoder = new SCryptPasswordEncoder(
            16,
            8,
            1,
            32,
            64);

   public String encrypt(String rawPassword){
        return encoder.encode(rawPassword);
   }

   public boolean matches(String rawPassword, String encryptedPassword){
        return encoder.matches(rawPassword, encryptedPassword);
   }
}

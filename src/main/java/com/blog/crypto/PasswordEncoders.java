package com.blog.crypto;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoders {


   public String encrypt(String rawPassword){
//        return encoder.encode(rawPassword);
       return "";
   }

   public boolean matches(String rawPassword, String encryptedPassword){
//        return encoder.matches(rawPassword, encryptedPassword);
       return true;
   }
}

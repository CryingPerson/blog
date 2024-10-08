package com.blog.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class blogException extends RuntimeException{


    public final Map<String, String> validation = new HashMap<>();

    public blogException(String message) {
        super(message);
    }

    public blogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String field, String message){
        validation.put(field,message);
    }
}

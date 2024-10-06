package com.blog.controller;


import com.blog.exception.InvalidRequest;
import com.blog.exception.blogException;
import com.blog.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invaildRequestHandler(MethodArgumentNotValidException e){
//            FieldError fieldError = e.getFieldError();
//            String field = fieldError.getField();
//            String defaultMessage = fieldError.getDefaultMessage();
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청 입니다.")
                .build();
         for(FieldError fieldError : e.getFieldErrors()){
             response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
         }

        // MethodArgumentNotValidException
//        e.getField()
        return response;
    }

    @ExceptionHandler(blogException.class)
    public ResponseEntity<ErrorResponse> blogExceptionHandler(blogException e){
        int statusCode = e.getStatusCode();

        System.out.println("Status Code: " + statusCode);
        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

//        if(e instanceof InvalidRequest){
//            InvalidRequest invalidRequest = (InvalidRequest) e;
//            String fieldName = invalidRequest.getFieldName();
//            String message = invalidRequest.getMessage();
//            body.addValidation(fieldName, message);
//        }
        //응답 json validation -> title : 제목에 바보를 포함할 수 없습니다.

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
                .body(body);

        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e){
        log.error("예외발생", e);

        ErrorResponse body = ErrorResponse.builder()
                .code("500")
                .message(e.getMessage())
                .build();

//        if(e instanceof InvalidRequest){
//            InvalidRequest invalidRequest = (InvalidRequest) e;
//            String fieldName = invalidRequest.getFieldName();
//            String message = invalidRequest.getMessage();
//            body.addValidation(fieldName, message);
//        }
        //응답 json validation -> title : 제목에 바보를 포함할 수 없습니다.

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(500)
                .body(body);

        return response;
    }

}

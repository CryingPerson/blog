package com.blog.exception;

import lombok.Getter;

@Getter
public class InvalidPassword extends blogException {

    /**
     * status -< 400
     */
    private static final String MESSAGE = "비밀번호가 올바르지 않ㅅ싑낟.";

    public InvalidPassword() {
        super(MESSAGE);
    }


    @Override
    public int getStatusCode(){
        return 400;
    }
}

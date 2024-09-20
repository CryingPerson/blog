package com.blog.exception;

import lombok.Getter;

@Getter
public class InvalidRequest extends blogException {

    /**
     * status -< 400
     */
    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }
    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);         // 여기 다봐라
        addValidation(fieldName, message);
//        this.fieldName = fieldName;
//        this.message = message;
    }

    @Override
    public int getStatusCode(){
        return 400;
    }
}

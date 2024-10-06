package com.blog.exception;

public class Unauthorized extends blogException {

    /**
     * status -< 401
     */
    private static final String MESSAGE = "인증이 필요합니다.";
    public Unauthorized() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}

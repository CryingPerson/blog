package com.blog.exception;

public class PostNotFound extends blogException {

    /**
     * status -< 404
     */
    private static final String MESSAGE = "존재하지 않는 글입니다.";
    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}

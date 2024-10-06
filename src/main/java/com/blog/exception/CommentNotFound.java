package com.blog.exception;

public class CommentNotFound extends blogException {

    /**
     * status -< 404
     */
    private static final String MESSAGE = "존재하지 않는 댓글 입니다.";
    public CommentNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}

package com.blog.request.comment;

import lombok.Getter;

@Getter
public class CommentDelete {
    public CommentDelete(String password) {
        this.password = password;
    }
    public CommentDelete() {
    }
    private String password;
}

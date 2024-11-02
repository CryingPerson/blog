package com.blog.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationDTO {
    private String message;
    private Long postId;
    private Long commentId;
}
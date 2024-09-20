package com.blog.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

        // 서비스의 정책을 넣지마세요!! 절대!!

    public PostEditor.PostEditorBuilder toEditor(){
      return  PostEditor.builder()
                .title(title)
                .content(content);

    }
//    public void edit(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }
//
    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }
}

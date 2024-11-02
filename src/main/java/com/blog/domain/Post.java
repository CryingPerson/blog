package com.blog.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn
    private AppUser user;

    @Column(nullable = false)
    private LocalDateTime regDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments;

    @Builder
    public Post(String title, String content, AppUser user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.regDate = LocalDateTime.now();
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

    public Long getUserId(){
        return this.user.getId();
    }

    public void addComment(Comment comment) {
        comment.setPost(this);
        this.comments.add(comment);
    }
}

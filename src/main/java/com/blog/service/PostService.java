package com.blog.service;

import com.blog.domain.AppUser;
import com.blog.domain.Post;
import com.blog.domain.PostEditor;
import com.blog.exception.PostNotFound;
import com.blog.exception.UserNotFound;
import com.blog.repository.post.PostRepository;
import com.blog.repository.UserRepository;
import com.blog.request.post.PostCreate;
import com.blog.request.post.PostEdit;
import com.blog.request.post.PostSearch;
import com.blog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private  final PostRepository postRepository;

    private final UserRepository userRepository;
    public void write(Long userId ,PostCreate postCreate){

        // repository.save(postCreate)
        AppUser user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        Post post =  Post.builder()
                .user(user)
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);

        // 클라이언트 요구사항
            // Json 응답에서 title 값 길이를 최대 10글자로 해주세요.
            // post entity < - > PostResponse class
    }

    public PostResponse get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    // 글이 너무 많은 경우 -> 비용이 너무 많이 든다.
    // 글이 -> 100,000,000 -> DB 글 모두 조회하는 경우 -> DB가 뻗을 수 있다.
    // DB -> 애플리케이션 서버로 전달하는 시간, 트래픽비용 등이 많이 발생할 수 있다.
    public List<PostResponse> getList(PostSearch postSearch) {
        // web -> page 1 -> 0
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);
        PostEditor.PostEditorBuilder EditorBuilder = post.toEditor();
        PostEditor postEditor = EditorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();
        post.edit(postEditor);

//        post.edit(postEdit.getTitle() != null ? postEdit.getTitle() : post.getTitle(),
//                postEdit.getContent() != null ? postEdit.getContent() : post.getContent());
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        // -> 존재하는 경우
        postRepository.delete(post);
    }
}

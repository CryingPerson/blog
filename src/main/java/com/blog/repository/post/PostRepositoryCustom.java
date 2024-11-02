package com.blog.repository.post;

import com.blog.domain.Post;
import com.blog.request.post.PostSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostRepositoryCustom {

    Page<Post> getList(PostSearch postSearch);
}

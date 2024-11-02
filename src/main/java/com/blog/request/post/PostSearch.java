package com.blog.request.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@Builder
public class PostSearch {

    private static final int MAX_SIZE= 2000;
    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    public long getOffset(){
        return (long) (Math.max(1, page) - 1 ) * Math.min(size, MAX_SIZE);
    }

    public Pageable getPageable(){
        return PageRequest.of(page - 1,size);
    }
}

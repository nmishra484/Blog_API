package com.blogapi.blogapi.Service;

import com.blogapi.blogapi.payload.postDto;

import java.util.List;

public interface PostService {

    public postDto createPost(postDto PostDto);

    postDto getPostById(long id);

    List<postDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    void deletePost(long id);

    postDto updatePost(long id, postDto postDto);

}

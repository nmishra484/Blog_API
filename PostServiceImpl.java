package com.blogapi.blogapi.Service.impl;

import com.blogapi.blogapi.Service.PostService;
import com.blogapi.blogapi.entity.post;
import com.blogapi.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.blogapi.payload.postDto;
import com.blogapi.blogapi.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo,ModelMapper modelMapper) {

        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public postDto createPost(postDto PostDto){

        post Post = mapToEntity(PostDto);

        post savedPost = postRepo.save(Post);

        postDto dto = maptoDto(savedPost);


        return dto;

    }

    @Override
    public postDto getPostById(long id) {
        post post = postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id)
        );
        postDto dto =  maptoDto(post);

        return dto;
    }

    @Override
    public List<postDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
      Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
        Page<post> posts = postRepo.findAll(pageable);
        List<post> content = posts.getContent();
        List<postDto>PostDtos= content.stream().map(post-> maptoDto(post)).collect(Collectors.toList());
        return PostDtos;
    }

    @Override
    public void deletePost(long id) {
        postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        postRepo.deleteById(id);
    }

    @Override
    public postDto updatePost(long id, postDto postDto) {
        post Post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );

        post updatedContent = mapToEntity(postDto);
        updatedContent.setId(Post.getId());
        post updatedPostInfo = postRepo.save(updatedContent);
        return maptoDto(updatedPostInfo);
    }


    postDto maptoDto(post Post){
    postDto dto = modelMapper.map(Post , postDto.class);
       // dto.setId(Post.getId());
       // dto.setTitle(Post.getTitle());
       // dto.setDescription(Post.getDescription());
       // dto.setContent(Post.getContent());
        return dto;
    }
    post mapToEntity(postDto PostDto){
        post Post = modelMapper.map(PostDto , post.class);
//        Post.setTitle(PostDto.getTitle());
//        Post.setDescription(PostDto.getDescription());
//        Post.setContent(PostDto.getContent());
        return Post;
    }
}

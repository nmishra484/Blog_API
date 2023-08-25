package com.blogapi.blogapi.Controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.blogapi.blogapi.Service.PostService;
import com.blogapi.blogapi.payload.postDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody postDto PostDto, BindingResult result) {//status code = 201
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
          postDto savedDto = postService.createPost(PostDto);
          return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
      }

    //http://localhost:8080/api/posts?pageNo=0&pageSize=3
    @GetMapping("/{id}")
    public ResponseEntity<postDto> getPostById(@PathVariable("id") long id) { //status code = 200
        postDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=title&sortBy=desc
    @GetMapping
    public List<postDto> getAllPosts(
            @RequestParam(value="pageNo", defaultValue = "0",required = false)int pageNo,
            @RequestParam(value="pageSize", defaultValue = "5",required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false)String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        List<postDto> Postdtos = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return Postdtos;
    }

    //http://localhost:8080/api/posts/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id) { // status code =200
        postService.deletePost(id);
        return new ResponseEntity<>("post is deleted!!", HttpStatus.OK);
    }

    //http://localhost:8080/api/posts/1
    @PutMapping("/{id}")
    public ResponseEntity<postDto> updatePost(@PathVariable("id") long id, @RequestBody postDto PostDto) { // status code =200
        postDto dto = postService.updatePost(id, PostDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}

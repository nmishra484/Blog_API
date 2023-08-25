package com.blogapi.blogapi.repository;

import com.blogapi.blogapi.entity.post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<post,Long> {

}

package com.blogapi.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class postDto {

    private long id;

    @NotEmpty
    @Size(min=2 , message="Title should be at least 2 character")
    private String title;

    @NotEmpty(message="should not be empty")
    @Size(min=4 , message="description at least 4 character")
    private String description;

    @NotEmpty(message="Name may not be empty")
    private String content;
}

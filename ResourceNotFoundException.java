package com.blogapi.blogapi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException { //status code 404

    public ResourceNotFoundException(long id){

        super("Resource not found for id:"+id);
    }
}

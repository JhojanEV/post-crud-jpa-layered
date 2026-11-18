package com.softer.post.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNoFoundException extends RuntimeException {
    public PostNoFoundException() {
        super("Post no encontrado");
    }
}

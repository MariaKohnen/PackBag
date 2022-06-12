package com.github.mariakohnen.packbag.backend.controller.status;

public class NameAlreadyExistException extends RuntimeException {
    public NameAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}

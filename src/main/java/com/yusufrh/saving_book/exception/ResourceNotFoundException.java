package com.yusufrh.saving_book.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { 
        super(message); 
    }
}
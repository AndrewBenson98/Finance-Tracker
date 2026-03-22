package com.benson.category_service.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {


    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}

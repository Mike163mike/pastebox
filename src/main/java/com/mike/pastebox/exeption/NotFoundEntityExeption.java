package com.mike.pastebox.exeption;

public class NotFoundEntityExeption extends RuntimeException {

    public NotFoundEntityExeption(String message) {
        super(message);
    }
}

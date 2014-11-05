package com.example.api;

import com.facebook.swift.codec.ThriftStruct;

@ThriftStruct
public final class InvalidUserException extends Exception {
    public InvalidUserException() {
        super("Invalid User");
    }
}

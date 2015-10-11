package com.example.contact.api;

import com.facebook.swift.codec.ThriftStruct;

@ThriftStruct
public final class ContactNotFoundException extends Exception {
    public ContactNotFoundException() {
        super("Contact doesn't exist");
    }
}

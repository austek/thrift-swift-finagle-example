package com.example.contact.api;

import com.example.contact.api.exception.ContactNotFoundException;
import com.example.contact.api.model.Contact;
import com.example.contact.api.model.ContactRequest;
import com.facebook.swift.service.ThriftException;
import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;
import com.twitter.util.Future;

import java.util.List;

@ThriftService
public interface ContactService {
    int ERROR_CODE_CONTACT_NOT_FOUND = 1000;

    @ThriftMethod
    Future<Contact> create(ContactRequest contactRequest);

    @ThriftMethod(exception = {
            @ThriftException(type = ContactNotFoundException.class, id=1)
    })
    Future<Contact> get(String id) throws ContactNotFoundException;

    @ThriftMethod(exception = {
            @ThriftException(type = ContactNotFoundException.class, id=1)
    })
    Future<List<Contact>> getAll() throws ContactNotFoundException;

    @ThriftMethod(exception = {
            @ThriftException(type = ContactNotFoundException.class, id=1)
    })
    Future<String> delete(String id) throws ContactNotFoundException;

    @ThriftMethod(exception = {
            @ThriftException(type = ContactNotFoundException.class, id=1)
    })
    Future<Contact> update(String id, ContactRequest contactRequest) throws ContactNotFoundException;
}

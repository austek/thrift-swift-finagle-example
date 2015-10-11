package com.example.api;

import com.example.api.model.Contact;
import com.example.api.model.ContactRequest;
import com.facebook.swift.service.ThriftException;
import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;
import com.twitter.util.Future;

import java.util.List;

@ThriftService
public interface ContactService {
    Future<Contact> create(ContactRequest contactRequest);

    @ThriftMethod(exception = {
            @ThriftException(type = ContactNotFoundException.class, id=1)
    })
    Future<Contact> get(Integer id) throws ContactNotFoundException;

    @ThriftMethod(exception = {
            @ThriftException(type = ContactNotFoundException.class, id=1)
    })
    Future<List<Contact>> get() throws ContactNotFoundException;

    @ThriftMethod(exception = {
            @ThriftException(type = ContactNotFoundException.class, id=1)
    })
    Future<Contact> delete() throws ContactNotFoundException;

    @ThriftMethod(exception = {
            @ThriftException(type = ContactNotFoundException.class, id=1)
    })
    Future<Contact> update(Integer id, ContactRequest contactRequest) throws ContactNotFoundException;
}

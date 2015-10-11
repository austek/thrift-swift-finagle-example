package com.example.contact.api;

import com.example.contact.api.exception.ContactNotFoundException;
import com.example.contact.api.model.Contact;
import com.example.contact.api.model.ContactRequest;
import com.facebook.swift.service.ThriftException;
import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;
import com.twitter.util.Future;

import java.util.List;
import java.util.UUID;

@ThriftService
public interface ContactService {
    Future<Contact> create(ContactRequest contactRequest);

    @ThriftMethod(exception = {
            @ThriftException(type = ContactNotFoundException.class, id=1)
    })
    Future<Contact> get(UUID id) throws ContactNotFoundException;

    @ThriftMethod(exception = {
            @ThriftException(type = ContactNotFoundException.class, id=1)
    })
    Future<List<Contact>> get() throws ContactNotFoundException;

    @ThriftMethod(exception = {
            @ThriftException(type = ContactNotFoundException.class, id=1)
    })
    void delete(UUID id) throws ContactNotFoundException;

    @ThriftMethod(exception = {
            @ThriftException(type = ContactNotFoundException.class, id=1)
    })
    Future<Contact> update(UUID id, ContactRequest contactRequest) throws ContactNotFoundException;
}

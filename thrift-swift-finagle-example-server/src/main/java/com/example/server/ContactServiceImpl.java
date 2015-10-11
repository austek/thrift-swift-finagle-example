package com.example.server;

import com.example.api.model.Contact;
import com.example.api.ContactService;
import com.example.api.ContactNotFoundException;
import com.example.api.model.ContactRequest;
import com.twitter.util.Future;
import com.twitter.util.Promise;

import java.util.List;

public class ContactServiceImpl implements ContactService {
    public Future<String> sayHello(Contact contact) throws ContactNotFoundException {
        final Promise<String> promise = new Promise<>();
        if(contact == null || contact.getName() == null || contact.getName().isEmpty()){
            promise.setException(new ContactNotFoundException());
        }else{
            promise.setValue("Hello "+ contact);
        }
        return promise;
    }

    @Override
    public Future<Contact> create(ContactRequest contactRequest) {
        return null;
    }

    @Override
    public Future<Contact> get(Integer id) throws ContactNotFoundException {
        return null;
    }

    @Override
    public Future<List<Contact>> get() throws ContactNotFoundException {
        return null;
    }

    @Override
    public Future<Contact> delete() throws ContactNotFoundException {
        return null;
    }

    @Override
    public Future<Contact> update(Integer id, ContactRequest contactRequest) throws ContactNotFoundException {
        return null;
    }
}

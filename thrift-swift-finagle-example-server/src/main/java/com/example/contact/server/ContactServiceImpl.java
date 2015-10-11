package com.example.contact.server;

import com.example.contact.api.model.Contact;
import com.example.contact.api.ContactService;
import com.example.contact.api.ContactNotFoundException;
import com.example.contact.api.model.ContactRequest;
import com.twitter.util.Future;
import com.twitter.util.Promise;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ContactServiceImpl implements ContactService {
    @Override
    public Future<Contact> create(ContactRequest contactRequest) {
        final Promise<Contact> promise = new Promise<>();
        Contact.Builder contactBuilder = Contact.builder()
                .id(contactRequest.getId())
                .name(contactRequest.getName())
                .surname(contactRequest.getSurname());

        if(contactRequest.getDob() != null){
            contactBuilder.dob(contactRequest.getDob());
        }

        if(StringUtils.isNotBlank(contactRequest.getNumber())){
            contactBuilder.number(contactRequest.getNumber());
        }

        if(StringUtils.isNotBlank(contactRequest.getEmail())){
            contactBuilder.email(contactRequest.getEmail());
        }

        promise.setValue(contactBuilder.build());

        return promise;
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

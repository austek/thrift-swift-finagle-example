package com.example.contact.server.dao.impl;

import com.example.contact.api.exception.ContactNotFoundException;
import com.example.contact.api.exception.DaoException;
import com.example.contact.api.exception.ValidationException;
import com.example.contact.api.model.Contact;
import com.example.contact.api.model.ContactRequest;
import com.example.contact.server.dao.ContactRepository;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class InMemoryRepository implements ContactRepository{
    @Override
    public Contact save(ContactRequest contactRequest) throws DaoException, ValidationException {
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
        return contactBuilder.build();
    }

    @Override
    public Contact find(Integer id) throws ContactNotFoundException {
        return null;
    }

    @Override
    public List<Contact> findAll() {
        return null;
    }

    @Override
    public Contact update(Integer id, ContactRequest contactRequest) throws ContactNotFoundException {
        return null;
    }

    @Override
    public void delete(Integer id) throws ContactNotFoundException {

    }
}

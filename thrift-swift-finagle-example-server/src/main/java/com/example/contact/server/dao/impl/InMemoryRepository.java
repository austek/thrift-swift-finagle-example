package com.example.contact.server.dao.impl;

import com.example.contact.api.exception.ContactNotFoundException;
import com.example.contact.api.exception.DaoException;
import com.example.contact.api.exception.ValidationException;
import com.example.contact.api.model.Contact;
import com.example.contact.api.model.ContactRequest;
import com.example.contact.server.dao.ContactRepository;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.inject.Singleton;

@Singleton
public class InMemoryRepository implements ContactRepository {
    private HashMap<Integer, Contact> repository = new HashMap<>();

    @Override
    public Contact save(ContactRequest contactRequest) throws DaoException, ValidationException {
        Contact.Builder contactBuilder = Contact.builder()
                .id(UUID.randomUUID())
                .name(contactRequest.getName())
                .surname(contactRequest.getSurname());

        if (contactRequest.getDob() != null) {
            contactBuilder.dob(contactRequest.getDob());
        }

        if (StringUtils.isNotBlank(contactRequest.getNumber())) {
            contactBuilder.number(contactRequest.getNumber());
        }

        if (StringUtils.isNotBlank(contactRequest.getEmail())) {
            contactBuilder.email(contactRequest.getEmail());
        }

        return repository.put(1, contactBuilder.build());
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

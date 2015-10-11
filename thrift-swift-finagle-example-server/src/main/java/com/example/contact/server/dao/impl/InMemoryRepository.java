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
    private HashMap<UUID, Contact> repository = new HashMap<>();

    @Override
    public Contact save(ContactRequest contactRequest) throws DaoException, ValidationException {
        UUID id = UUID.randomUUID();
        Contact.Builder contactBuilder = Contact.builder()
                .id(id)
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

        return repository.put(id, contactBuilder.build());
    }

    @Override
    public Contact find(UUID id) throws ContactNotFoundException {
        Contact contact = repository.get(id);
        if(contact == null){
            throw new ContactNotFoundException();
        }
        return contact;
    }

    @Override
    public List<Contact> findAll() {
        return null;
    }

    @Override
    public Contact update(UUID id, ContactRequest contactRequest) throws ContactNotFoundException {
        return null;
    }

    @Override
    public void delete(UUID id) throws ContactNotFoundException {

    }
}

package com.github.rojanu.contact.server.dao;

import com.github.rojanu.contact.api.exception.ContactNotFoundException;
import com.github.rojanu.contact.api.exception.DaoException;
import com.github.rojanu.contact.api.exception.ValidationException;
import com.github.rojanu.contact.api.model.Contact;
import com.github.rojanu.contact.api.model.ContactRequest;

import java.util.List;
import java.util.UUID;

public interface ContactRepository {

    Contact save(ContactRequest contactRequest) throws DaoException, ValidationException;

    Contact find(UUID id) throws ContactNotFoundException;

    List<Contact> findAll();

    Contact update(UUID id, ContactRequest contact) throws ContactNotFoundException;

    void delete(UUID id) throws ContactNotFoundException;
}

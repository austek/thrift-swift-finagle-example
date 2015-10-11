package com.example.contact.server.dao;

import com.example.contact.api.exception.ContactNotFoundException;
import com.example.contact.api.exception.DaoException;
import com.example.contact.api.exception.ValidationException;
import com.example.contact.api.model.Contact;
import com.example.contact.api.model.ContactRequest;

import java.util.List;

public interface ContactRepository {

    Contact save(ContactRequest contactRequest) throws DaoException, ValidationException;

    Contact find(Integer id) throws ContactNotFoundException;

    List<Contact> findAll();

    Contact update(Integer id, ContactRequest contact) throws ContactNotFoundException;

    void delete(Integer id) throws ContactNotFoundException;
}

package com.github.rojanu.contact.server;

import com.github.rojanu.contact.api.ContactService;
import com.github.rojanu.contact.api.exception.ContactNotFoundException;
import com.github.rojanu.contact.api.exception.DaoException;
import com.github.rojanu.contact.api.exception.ValidationException;
import com.github.rojanu.contact.api.model.Contact;
import com.github.rojanu.contact.api.model.ContactRequest;
import com.github.rojanu.contact.server.dao.ContactRepository;
import com.github.rojanu.service.ThriftServiceException;
import com.twitter.finagle.stats.Counter;
import com.twitter.finagle.stats.LoadedStatsReceiver;
import com.twitter.finagle.stats.StatsReceiver;
import com.twitter.util.Future;
import com.twitter.util.Promise;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    private final StatsReceiver statsReceiver = LoadedStatsReceiver.scope("contact-manager");
    private final Counter contactCnt = statsReceiver.counter0("contact-cnt");

    public ContactServiceImpl(final ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Future<Contact> create(ContactRequest contactRequest) {
        final Promise<Contact> promise = new Promise<>();

        try {
            promise.setValue(contactRepository.save(contactRequest));
            contactCnt.incr();
        } catch (DaoException | ValidationException e) {
            promise.setException(e);
        }

        return promise;
    }

    @Override
    public Future<Contact> get(String id){
        if(StringUtils.isBlank(id)){
            return Future.exception(new ContactNotFoundException());
        }
        try {
            return Future.value(contactRepository.find(UUID.fromString(id)));
        } catch (ContactNotFoundException e) {
            return Future.exception(e);
        }
    }

    @Override
    public Future<List<Contact>> getAll() throws ContactNotFoundException {
        final Promise<List<Contact>> promise = new Promise<>();
        promise.setValue(contactRepository.findAll());
        return promise;
    }

    @Override
    public Future<String> delete(String id) {
        if(StringUtils.isBlank(id)){
            return Future.exception(new ContactNotFoundException());
        }
        try {
            contactRepository.delete(UUID.fromString(id));
            return Future.value("");
        } catch (ContactNotFoundException e) {
            return Future.exception(e);
        }
    }

    @Override
    public Future<Contact> update(String id, ContactRequest contactRequest) {
        if(StringUtils.isBlank(id)){
            return Future.exception(new ContactNotFoundException());
        }
        final Promise<Contact> promise = new Promise<>();
        try {
            promise.setValue(contactRepository.update(UUID.fromString(id), contactRequest));
        } catch (ContactNotFoundException e) {
            return Future.exception(e);
        }

        return promise;
    }

    @Override
    public Future<String> getName() {
        return Future.value("contact-service");
    }

    @Override
    public Future<String> getVersion() {
        return Future.value("1.0.0");
    }

    @Override
    public Future<String> getBuildInfo() throws ThriftServiceException {
        return Future.value("abcde-efddlldl-ds1333");
    }
}

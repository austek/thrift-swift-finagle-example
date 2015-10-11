package com.example.contact.api.model;

import com.facebook.swift.codec.ThriftConstructor;
import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

import javax.annotation.concurrent.Immutable;

@Immutable
@ThriftStruct(builder = ContactRequest.Builder.class)
public class ContactRequest {
    /* MANDATORY FIELDS */
    private Integer id;
    private String name;
    private String surname;
    /* MANDATORY FIELDS */

    private String number;
    private String email;
    private Long dob;

    @ThriftConstructor
    public ContactRequest(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.surname = builder.surname;
        this.number = builder.number;
        this.email = builder.email;
        this.dob = builder.dob;
    }

    @ThriftField(1)
    public Integer getId() {
        return id;
    }

    @ThriftField(2)
    public String getName() {
        return name;
    }

    @ThriftField(3)
    public String getSurname() {
        return surname;
    }

    @ThriftField(4)
    public String getNumber() {
        return number;
    }

    @ThriftField(5)
    public String getEmail() {
        return email;
    }

    @ThriftField(6)
    public Long getDob() {
        return dob;
    }

    public static Id builder() {
        return new Builder();
    }

    public static class Builder implements Id, Name, Surname {
        /* MANDATORY FIELDS */
        private Integer id;
        private String name;
        private String surname;
        /* MANDATORY FIELDS */

        private String number;
        private String email;
        private Long dob;

        @ThriftField
        public Name id(Integer id) {
            this.id = id;
            return this;
        }

        @ThriftField
        public Surname name(String name) {
            this.name = name;
            return this;
        }

        @ThriftField
        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        @ThriftField
        public Builder number(String number) {
            this.number = number;
            return this;
        }

        @ThriftField
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        @ThriftField
        public Builder dob(Long dob) {
            this.dob = dob;
            return this;
        }

        @ThriftConstructor
        public ContactRequest build() {
            return new ContactRequest(this);
        }

    }

    public interface Id {
        Name id(Integer id);
    }

    public interface Name {
        Surname name(String name);
    }

    public interface Surname {
        Builder surname(String surname);
    }
}

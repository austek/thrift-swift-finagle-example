package com.github.rojanu.contact.api.model;

import com.facebook.swift.codec.ThriftConstructor;
import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

import javax.annotation.concurrent.Immutable;

@Immutable
@ThriftStruct(builder = ContactRequest.Builder.class)
public final class ContactRequest {
    /* MANDATORY FIELDS */
    private String name;
    private String surname;
    /* MANDATORY FIELDS */

    private String number;
    private String email;
    private Long dob;

    public ContactRequest(Builder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.number = builder.number;
        this.email = builder.email;
        this.dob = builder.dob;
    }

    @ThriftField(1)
    public String getName() {
        return name;
    }

    @ThriftField(2)
    public String getSurname() {
        return surname;
    }

    @ThriftField(3)
    public String getNumber() {
        return number;
    }

    @ThriftField(4)
    public String getEmail() {
        return email;
    }

    @ThriftField(5)
    public Long getDob() {
        return dob;
    }

    public static Name builder() {
        return new Builder();
    }

    public static class Builder implements Name, Surname {
        /* MANDATORY FIELDS */
        private String name;
        private String surname;
        /* MANDATORY FIELDS */

        private String number;
        private String email;
        private Long dob;

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

    public interface Name {
        Surname name(String name);
    }

    public interface Surname {
        Builder surname(String surname);
    }
}

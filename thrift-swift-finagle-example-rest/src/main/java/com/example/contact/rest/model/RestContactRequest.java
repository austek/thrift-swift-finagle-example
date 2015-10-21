package com.example.contact.rest.model;

import com.example.contact.api.model.ContactRequest;

import org.hibernate.validator.constraints.NotBlank;

public class RestContactRequest {
    /* MANDATORY FIELDS */
    @NotBlank
    private String name;

    @NotBlank
    private String surname;
    /* MANDATORY FIELDS */

    private String number;
    private String email;
    private Long dob;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDob() {
        return dob;
    }

    public void setDob(Long dob) {
        this.dob = dob;
    }

    public static ContactRequest to(RestContactRequest restContactRequest){
        return ContactRequest.builder()
                .name(restContactRequest.getName())
                .surname(restContactRequest.getSurname())
                .number(restContactRequest.getNumber())
                .dob(restContactRequest.getDob())
                .email(restContactRequest.getEmail())
                .build();
    }
}

package com.example.api;

import com.facebook.swift.codec.ThriftConstructor;
import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

@ThriftStruct
public final class User {
    private Integer id;
    private String name;

    public User() {}

    @ThriftConstructor
    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @ThriftField(1)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ThriftField(2)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

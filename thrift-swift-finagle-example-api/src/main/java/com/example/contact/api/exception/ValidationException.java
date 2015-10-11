package com.example.contact.api.exception;

import com.facebook.swift.codec.ThriftConstructor;
import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

@ThriftStruct
public final class ValidationException extends Exception {
    private int errorCode;
    private String field;
    private String message;

    public ValidationException(int errorCode, String message) {
        this(errorCode, null, message);
    }

    @ThriftConstructor
    public ValidationException(int errorCode, String field, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.field = field;
    }

    @ThriftField(1)
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @ThriftField(2)
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @ThriftField(3)
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidationException that = (ValidationException) o;

        if (errorCode != that.errorCode) return false;
        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = errorCode;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (field != null ? field.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ValidationException{" +
                "errorCode=" + errorCode +
                ", field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

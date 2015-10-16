package com.example.config.encryption;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EncryptedStringDeserializer extends JsonDeserializer<String> {

    public static final Logger logger = LoggerFactory.getLogger(EncryptedStringDeserializer.class);
    private final StringEncryptor stringEncryptor;

    public EncryptedStringDeserializer(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String str = jp.getText();
        if (str == null) {
            return null;
        } else {
            return stringEncryptor.decrypt(str);
        }
    }
}

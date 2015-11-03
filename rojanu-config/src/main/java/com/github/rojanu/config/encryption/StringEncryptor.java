package com.github.rojanu.config.encryption;

import com.google.common.io.Files;

import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public enum StringEncryptor {
    INSTANCE();

    private final Logger logger = LoggerFactory.getLogger(StringEncryptor.class);

    public static final String DEFAULT_PASSWORD_ENVIRONMENT_VARIABLE = "CONFIG_ENC_PASSWORD";
    public static final String DEFAULT_PASSWORD_ENVIRONMENT_FILE = ".config-enc-pass";
    public static final String ENC_PREFIX = "ENC(";
    public static final String DEFAULT_ALGORITHM = "PBEWITHSHA256AND256BITAES-CBC-BC";
    public static final String DEFAULT_CONFIG_PASSWORD = "password";

    private final String configPasswordVariable = System.getProperty("password-environment-variable", DEFAULT_PASSWORD_ENVIRONMENT_VARIABLE);
    private final String configPasswordFile = System.getProperty("password-file", DEFAULT_PASSWORD_ENVIRONMENT_FILE);

    private StandardPBEStringEncryptor stringEncryptor;

    private StringEncryptor() {
        stringEncryptor = new StandardPBEStringEncryptor();
        try {
            stringEncryptor.setAlgorithm(DEFAULT_ALGORITHM);
            stringEncryptor.setPassword(getPassword());
        } catch (Exception ex) {
            logger.error("error initializing jasypt encryptor!", ex);
        }
    }

    private String getPassword() {
        String configPassword;
        try {
            configPassword= Files.toString(new File(configPasswordFile), Charset.forName("US-ASCII"));
            logger.info("Configuration password load from file: " + configPasswordFile);
        } catch (IOException ex) {
            configPassword = System.getenv(configPasswordVariable);
            if(StringUtils.isNotEmpty(configPassword)){
                logger.info("Configuration password load from environment variable: "+ configPasswordVariable);
            }else{
                configPassword = DEFAULT_CONFIG_PASSWORD;
                logger.warn("Configuration password couldn't be found");
            }
        }
        return configPassword;
    }

    public String decrypt(final String str) {
        if (StringUtils.startsWith(str, ENC_PREFIX)) {
            String encryptedText = StringUtils.substring(str, 4, StringUtils.length(str) - 1);
            if (stringEncryptor != null) {
                try {
                    return stringEncryptor.decrypt(encryptedText);
                } catch (EncryptionInitializationException ex) {
                    logger.error("error in decrypting", ex);
                    System.err.println("error in decrypting ... " + ex.getMessage());
                    System.exit(-1);
                } catch (Exception ex) {
                    logger.error("error in decrypting", ex);
                    System.err.println("error in decrypting ... " + ex.getMessage());
                    System.exit(-1);
                }
            } else {
                logger.error("stringEncryptor is null -> can't decrypt");
                return encryptedText;
            }
        }
        return str;
    }

    public String encrypt(final String str){
        if (stringEncryptor != null) {
            return stringEncryptor.encrypt(str);
        }
        return str;
    }
}

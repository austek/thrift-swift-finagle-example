package com.github.rojanu.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.rojanu.config.encryption.EncryptedStringDeserializer;
import com.github.rojanu.config.encryption.StringEncryptor;
import com.github.rojanu.util.io.FileUtils;
import com.github.rojanu.util.io.Resource;
import com.google.common.base.Preconditions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public abstract class Config {
    public final Config $this = this;

    public Resource configFolder;

    public static <T extends Config> T load(Class<T> configClazz, String configPath) throws IOException, ConfigValidationException {
        return load(configClazz, configPath, null);
    }

    public static <T extends Config> T load(Class<T> configClazz, String configPath, String defaultConfigPath) throws IOException, ConfigValidationException {
        Objects.requireNonNull(configClazz);
        Objects.requireNonNull(configPath);
        InputStream defaultConfigInput = null;
        if (defaultConfigPath != null) {
            defaultConfigInput = FileUtils.getInputStream(Preconditions.checkNotNull(defaultConfigPath));
        }
        try (InputStream configInput = FileUtils.getInputStream(Preconditions.checkNotNull(configPath))) {
            return load(configClazz, configInput, configPath, defaultConfigInput);
        } finally {
            if (defaultConfigInput != null) {
                try {
                    defaultConfigInput.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public static <T extends Config> T load(Class<T> configClazz, InputStream configInput, String configPath) throws IOException, ConfigValidationException {
        return load(configClazz, configInput, configPath, null);
    }

    public static <T extends Config> T load(Class<T> configClazz, InputStream configInput, String configPath, InputStream defaultConfigInput) throws IOException, ConfigValidationException {
        Objects.requireNonNull(configClazz);
        Objects.requireNonNull(configInput);
        final ObjectMapper mapper = createObjectMapper();
        T defaultConfig;
        ObjectReader updater = null;
        if (defaultConfigInput != null) {
            defaultConfig = mapper.readValue(defaultConfigInput, configClazz);
            updater = mapper.readerForUpdating(defaultConfig);
        }
        T config;
        if (updater != null) {
            config = updater.readValue(configInput);
        } else {
            config = mapper.readValue(configInput, configClazz);
        }
        config.configFolder = Resource.from(configPath).getParent();
        //validate(config);
        return config;
    }

    private static ObjectMapper createObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.registerModule(getConfigEncryptionModule());
        return mapper;
    }

    private static SimpleModule getConfigEncryptionModule() {
        SimpleModule encryptionModule = new SimpleModule("encryption-module", new Version(1, 0, 0, null, "com.sagepay", "encryption-module"));
        encryptionModule.addDeserializer(String.class, new EncryptedStringDeserializer(StringEncryptor.INSTANCE));
        return encryptionModule;
    }

    public static <T extends Config> T load(TypeReference<?> configClazz, String configPath) throws IOException, ConfigValidationException {
        return load(configClazz, configPath, null);
    }

    public static <T extends Config> T load(TypeReference<?> configClazz, String configPath, String defaultConfigPath) throws IOException, ConfigValidationException {
        Objects.requireNonNull(configClazz);
        Objects.requireNonNull(configPath);
        InputStream defaultConfigInput = null;
        if (defaultConfigPath != null) {
            defaultConfigInput = FileUtils.getInputStream(Preconditions.checkNotNull(defaultConfigPath));
        }
        try (InputStream configInput = FileUtils.getInputStream(Preconditions.checkNotNull(configPath))) {
            return load(configClazz, configInput, configPath, defaultConfigInput);
        } finally {
            if (defaultConfigInput != null) {
                try {
                    defaultConfigInput.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public static <T extends Config> T load(TypeReference<?> configClazz, InputStream configInput, String configPath) throws IOException, ConfigValidationException {
        return load(configClazz, configInput, configPath, null);
    }

    public static <T extends Config> T load(TypeReference<?> configClazz, InputStream configInput, String configPath, InputStream defaultConfigInput) throws IOException, ConfigValidationException {
        Objects.requireNonNull(configClazz);
        Objects.requireNonNull(configInput);
        final ObjectMapper mapper = createObjectMapper();
        T defaultConfig;
        ObjectReader updater = null;
        if (defaultConfigInput != null) {
            defaultConfig = mapper.readValue(defaultConfigInput, configClazz);
            updater = mapper.readerForUpdating(defaultConfig);
        }
        T config;
        if (updater != null) {
            config = updater.readValue(configInput);
        } else {
            config = mapper.readValue(configInput, configClazz);
        }
        config.configFolder = Resource.from(configPath).getParent();
        //validate(config);
        return config;
    }

    public Resource relativize(Resource resource) {
        return configFolder.relativize(resource);
    }

    public Resource relativize(String path) {
        return configFolder.relativize(path);
    }
}

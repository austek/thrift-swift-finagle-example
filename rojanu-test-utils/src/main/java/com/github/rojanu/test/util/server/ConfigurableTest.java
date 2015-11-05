package com.github.rojanu.test.util.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.rojanu.config.Config;
import com.github.rojanu.config.ConfigValidationException;

import java.io.IOException;

public class ConfigurableTest<T extends Config> {
    private static final String CLIENT_CONFIG_FILENAME = "client.yaml";

    protected T loadConfig(Class<T> clazz) throws IOException, ConfigValidationException {
        return T.load(clazz, "classpath:config/" + getEnv() + "/" + CLIENT_CONFIG_FILENAME);
    }

    protected  T loadConfig(TypeReference<?> clazz) throws IOException, ConfigValidationException {
        return Config.load(clazz, "classpath:config/" + getEnv() + "/" + CLIENT_CONFIG_FILENAME);
    }
    protected  T loadConfig(TypeReference<?> clazz,String configFileName) throws IOException, ConfigValidationException {
        return Config.load(clazz, "classpath:config/" + getEnv() + "/" + configFileName);
    }
    
    protected String getEnv() {
        return System.getProperty("APP_ENV", "development");
    }
}

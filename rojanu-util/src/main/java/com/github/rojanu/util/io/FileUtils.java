package com.github.rojanu.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Pattern;

public final class FileUtils {
    private static final Pattern WINDOWS_DIRECTORY_SEPARATOR = Pattern.compile("\\\\+");
    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    private FileUtils() {
    }

    public static InputStream getInputStream(String location) {
        if (location == null || location.isEmpty()) {
            return null;
        }
        location = getCorrectedFilePath(location);
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return getInputStreamFromClasspath(location.substring(CLASSPATH_URL_PREFIX.length()));
        }
        return getInputStreamFromFileSystem(location);
    }

    private static String getCorrectedFilePath(final String location){
        return WINDOWS_DIRECTORY_SEPARATOR.matcher(location).replaceAll("/");
    }

    private static InputStream getInputStreamFromClasspath(String path) {
        return FileUtils.class.getClassLoader().getResourceAsStream(path);
    }

    private static InputStream getInputStreamFromFileSystem(String path) {
        if (!(new File(path)).exists()) {
            return null;
        }
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}

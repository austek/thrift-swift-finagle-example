package com.example.util.io;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class Resource {
    private static final String CLASSPATH_PREFIX = "classpath:";

    private final Resource parent;
    private final String path;

    private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public static Resource from(final String path) {
        return new Resource((Resource) null, path);
    }

    public static Resource from(final String parentPath, final String path) {
        return new Resource(Resource.from(parentPath), path);
    }

    public static Resource from(final Resource parent, final String path) {
        return new Resource(parent, path);
    }

    public static Resource from(final Resource parent, final Resource resource) {
        return new Resource(parent, resource.getExpandedPath());
    }

    private Resource(final Resource parent, final String path) {
        this.parent = parent;
        this.path = path;
    }

    private String removeStartingSlash(String path) {
        return StringUtils.removeStart(path, "/");
    }

    private String getFolderPath(String path, boolean isClasspath) {
        if (StringUtils.endsWithAny(path, new String[] {"\\" , "/"}))
            return path;
        return path + (isClasspath ? "/" : File.separator);
    }

    public String getExpandedPath() {
        String parentPath = parent != null ? parent.getExpandedPath() : null;
        if (parentPath != null) {
            if (StringUtils.startsWith(parentPath, CLASSPATH_PREFIX)) {
                String parentPath2 = StringUtils.removeStart(parentPath, CLASSPATH_PREFIX);
                if (StringUtils.startsWith(path, CLASSPATH_PREFIX)) {
                    String path2 = StringUtils.removeStart(path, CLASSPATH_PREFIX);
                    if (StringUtils.startsWithAny(path2, new String[] {"/", "\\"})) {
                        return CLASSPATH_PREFIX + removeStartingSlash(path2);
                    } else {
                        return CLASSPATH_PREFIX + getFolderPath(removeStartingSlash(parentPath2), true) + path2;
                    }
                } else {
                    String path2 = StringUtils.removeStart(path, CLASSPATH_PREFIX);
                    if (new File(path2).isAbsolute()) {
                        return CLASSPATH_PREFIX + path2;
                    } else {
                        return CLASSPATH_PREFIX + getFolderPath(removeStartingSlash(parentPath2), true) + path2;
                    }
                }
            } else {
                if (StringUtils.startsWith(path, CLASSPATH_PREFIX)) {
                    throw new IllegalArgumentException("classpath resource with non-classpath parent resource!");
                }
                return new File(path).isAbsolute() ? path : getFolderPath(parentPath, false) + path;
            }
        } else {
            return path;
        }
    }

    public InputStream openStream() throws FileNotFoundException {
        String expandedPath = getExpandedPath();
        if (StringUtils.startsWith(expandedPath, CLASSPATH_PREFIX)) {
            return classLoader.getResourceAsStream(StringUtils.removeStart(expandedPath, CLASSPATH_PREFIX));
        }
        return new FileInputStream(expandedPath);

    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        Objects.requireNonNull(classLoader);
        this.classLoader = classLoader;
    }

    public Resource getParent() {
        String expandedPath = getExpandedPath();
        String parentPath = new File(expandedPath).getParent();
        if (StringUtils.startsWith(parentPath, CLASSPATH_PREFIX)) {
            parentPath = StringUtils.replace(parentPath, "\\", "/");
        }
        return Resource.from(parentPath);
    }

    public Resource relativize(Resource resource) {
        return Resource.from(this, resource);
    }

    public Resource relativize(String path) {
        return Resource.from(this, path);
    }
}

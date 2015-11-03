package com.github.rojanu.config;

import com.github.rojanu.validation.ConstraintViolations;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class ConfigValidationException extends Exception {
    private static final long serialVersionUID = 5325162099634227047L;

    private final ImmutableSet<ConstraintViolation<?>> constraintViolations;

    public <T> ConfigValidationException(Set<ConstraintViolation<T>> errors) {
        super(formatMessage(errors));
        this.constraintViolations = ConstraintViolations.copyOf(errors);
    }

    public ImmutableSet<ConstraintViolation<?>> getConstraintViolations() {
        return constraintViolations;
    }

    protected static <T> String formatMessage(Set<ConstraintViolation<T>> violations) {
        final StringBuilder msg = new StringBuilder();
        msg.append(violations.size() == 1 ? " has an error:" : " has the following errors:").append("\r\n");
        for (ConstraintViolation<?> v : violations) {
            msg.append("  * ").append(ConstraintViolations.format(v)).append("\r\n");
        }
        return msg.toString();
    }

}

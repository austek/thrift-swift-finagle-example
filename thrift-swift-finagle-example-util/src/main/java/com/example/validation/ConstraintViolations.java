package com.example.validation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class ConstraintViolations {
    private ConstraintViolations() { /* singleton */ }

    public static <T> String format(ConstraintViolation<T> v) {
        return String.format("%s %s (was %s)",
                v.getPropertyPath(),
                v.getMessage(),
                v.getInvalidValue());
    }

    public static <T> ImmutableList<String> format(Set<ConstraintViolation<T>> violations) {
        final Set<String> errors = Sets.newHashSet();
        for (ConstraintViolation<?> v : violations) {
            errors.add(format(v));
        }
        return ImmutableList.copyOf(Ordering.natural().sortedCopy(errors));
    }

    public static ImmutableList<String> formatUntyped(Set<ConstraintViolation<?>> violations) {
        final Set<String> errors = Sets.newHashSet();
        for (ConstraintViolation<?> v : violations) {
            errors.add(format(v));
        }
        return ImmutableList.copyOf(Ordering.natural().sortedCopy(errors));
    }

    public static <T> ImmutableSet<ConstraintViolation<?>> copyOf(Set<ConstraintViolation<T>> violations) {
        final ImmutableSet.Builder<ConstraintViolation<?>> builder = ImmutableSet.builder();
        for (ConstraintViolation<T> violation : violations) {
            builder.add(violation);
        }
        return builder.build();
    }
}

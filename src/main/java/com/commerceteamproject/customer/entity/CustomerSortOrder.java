package com.commerceteamproject.customer.entity;

import java.util.Arrays;

public enum CustomerSortOrder {
    asc, desc;

    public static boolean exists(String value) {
        return Arrays.stream(values())
                .anyMatch(e -> e.name().equals(value));
    }
}

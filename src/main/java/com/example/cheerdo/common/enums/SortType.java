package com.example.cheerdo.common.enums;

import java.util.Arrays;

public enum SortType {
    ASC,
    DESC,
    DEFAULT;

    public static SortType of(String sortType) {

        return Arrays.stream(values()).
                filter(s -> s.name().equals(sortType))
                .findFirst()
                .orElse(DEFAULT);
    }


}

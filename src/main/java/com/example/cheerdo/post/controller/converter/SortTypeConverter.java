package com.example.cheerdo.post.controller.converter;

import com.example.cheerdo.common.enums.SortType;
import org.springframework.core.convert.converter.Converter;

public class SortTypeConverter implements Converter<String, SortType> {
    @Override
    public SortType convert(String source) {
        return SortType.of(source);
    }
}

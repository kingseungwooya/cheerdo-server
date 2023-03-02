package com.example.cheerdo.common.sort;

import com.example.cheerdo.common.enums.SortType;
import com.example.cheerdo.todo.controller.TodoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

public class SortUtil {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private SortUtil() {

    }
    public static Sort sort(SortType type, String properties) {
        switch (type) {
            case DESC -> {
                logger.info("entry of DESC and input type is -> {}", type);
                return Sort.by(Sort.Direction.DESC, properties);
            }
            default -> {

                return Sort.by(Sort.Direction.ASC, properties);
            }
        }
    }
}

package ru.kopyshev.rvs.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.util.List;

public class JsonUtil {
    public static <T> List<T> readValues(String json, Class<T> tClass) {
        ObjectReader reader = JacksonObjectMapper.getMapper().readerFor(tClass);
        try {
            return reader.<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read array from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> T readValue(String json, Class<T> tClass) {
        try {
            return JacksonObjectMapper.getMapper().readValue(json, tClass);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> String writeValue(T obj) {
        try {
            return JacksonObjectMapper.getMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }
}
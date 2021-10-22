package ru.kopyshev.rvs.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalTime;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(String source) {
        return StringUtils.hasLength(source) ? LocalTime.parse(source) : null;
    }
}

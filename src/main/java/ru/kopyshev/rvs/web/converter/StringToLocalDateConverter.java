package ru.kopyshev.rvs.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public @Nullable LocalDate convert(@Nullable String source) {
        return StringUtils.hasLength(source) ? LocalDate.parse(source) : null;
    }
}

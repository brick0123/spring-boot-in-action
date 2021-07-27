package com.jinho.typeconverter.converter;

import org.springframework.core.convert.converter.Converter;

public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(final String source) {
        return Integer.valueOf(source);
    }
}

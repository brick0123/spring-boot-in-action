package com.jinho.typeconverter.formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

public class MyNumberFormatter implements Formatter<Number> {

    @Override
    public Number parse(final String text, final Locale locale) throws ParseException {
        return NumberFormat.getInstance(locale).parse(text);
    }

    @Override
    public String print(final Number object, final Locale locale) {
        return NumberFormat.getInstance(locale).format(object);
    }
}

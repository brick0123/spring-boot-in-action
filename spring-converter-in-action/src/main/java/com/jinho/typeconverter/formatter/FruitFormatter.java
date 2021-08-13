package com.jinho.typeconverter.formatter;

import com.jinho.typeconverter.type.Fruit;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class FruitFormatter implements Formatter<Fruit> {

    @Override
    public Fruit parse(final String text, final Locale locale) throws ParseException {
        return Fruit.findByColor(text);
    }

    @Override
    public String print(final Fruit object, final Locale locale) {
        return null;
    }
}

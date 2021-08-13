package com.jinho.typeconverter.formatter;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.typeconverter.type.Fruit;
import java.text.ParseException;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class FruitFormatterTest {

    FruitFormatter formatter = new FruitFormatter();

    @Test
    void parse() throws ParseException {
        final Fruit fruit = formatter.parse("RED", Locale.KOREA);
        assertThat(fruit).isEqualTo(Fruit.APPLE);
    }
}

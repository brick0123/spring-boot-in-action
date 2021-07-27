package com.jinho.typeconverter.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.jinho.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;

class ConverterTest {

    @Test
    void stringToInteger() {
        final StringToIntegerConverter converter = new StringToIntegerConverter();
        final Integer result = converter.convert("10");
        assertThat(result).isEqualTo(10);
    }

    @Test
    void integerToString() {
        final IntegerToStringConverter converter = new IntegerToStringConverter();
        final String result = converter.convert(10);
        assertThat(result).isEqualTo("10");
    }

    @Test
    void stringToIpPort() {
        final IpPortToStringConverter converter = new IpPortToStringConverter();
        final IpPort source = new IpPort("127.0.0.1", 8080);

        final String result = converter.convert(source);
        assertThat(result).isEqualTo("127.0.0.1:8080");
    }

    @Test
    void ipPortToString() {
        final StringToIpPortConverter converter = new StringToIpPortConverter();
        String source = "127.0.0.1:8080";
        final IpPort result = converter.convert(source);

        assertThat(result).isEqualTo(new IpPort("127.0.0.1", 8080));
    }
}

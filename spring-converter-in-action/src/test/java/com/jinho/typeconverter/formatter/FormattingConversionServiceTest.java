package com.jinho.typeconverter.formatter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

public class FormattingConversionServiceTest {

    @Test
    void formattingConversionService() {
        final DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        conversionService.addFormatter(new MyNumberFormatter());

        assertThat(conversionService.convert("1,000", Number.class)).isEqualTo(1000L);
        assertThat(conversionService.convert(1000, String.class)).isEqualTo("1,000");
    }
}

package com.jinho.typeconverter;

import com.jinho.typeconverter.converter.IntegerToStringConverter;
import com.jinho.typeconverter.converter.IpPortToStringConverter;
import com.jinho.typeconverter.converter.StringToIntegerConverter;
import com.jinho.typeconverter.converter.StringToIpPortConverter;
import com.jinho.typeconverter.formatter.FruitFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        final DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());

        conversionService.addFormatter(new FruitFormatter());
    }
}

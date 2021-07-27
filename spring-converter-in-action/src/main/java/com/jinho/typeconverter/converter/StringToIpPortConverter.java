package com.jinho.typeconverter.converter;

import com.jinho.typeconverter.type.IpPort;
import org.springframework.core.convert.converter.Converter;

public class StringToIpPortConverter implements Converter<String, IpPort> {

    @Override
    public IpPort convert(final String source) {
        final String[] split = source.split(":");
        return new IpPort(split[0], Integer.parseInt(split[1]));
    }
}

package com.jinho.typeconverter.converter;


import com.jinho.typeconverter.type.IpPort;
import org.springframework.core.convert.converter.Converter;

public class IpPortToStringConverter implements Converter<IpPort, String > {

    @Override
    public String convert(final IpPort source) {
        return source.getIp() + ":" + source.getPort();
    }
}

package com.jinho.typeconverter.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class IpPort {
    private String ip;
    private int port;

    public IpPort(final String ip, final int port) {
        this.ip = ip;
        this.port = port;
    }
}

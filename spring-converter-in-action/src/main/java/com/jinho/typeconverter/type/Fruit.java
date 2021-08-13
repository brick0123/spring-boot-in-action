package com.jinho.typeconverter.type;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Fruit {

    APPLE("RED"),
    BANANA("YELLOW");

    private final String color;

    public static Fruit findByColor(final String color) {
        return Arrays.stream(Fruit.values())
            .filter(f -> f.getColor().equalsIgnoreCase(color))
            .findAny()
            .orElseThrow(IllegalStateException::new);
    }
}

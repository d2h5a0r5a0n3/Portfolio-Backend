package com.d2h5a0r5a0n3.portfolio.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class EnumUtils {
    public <E extends Enum<E>> List<String> getFormattedEnumValues(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(this::formatEnumName)
                .collect(Collectors.toList());
    }

    private String formatEnumName(Enum<?> e) {
        String name = e.name().replace("_", " ").toLowerCase();
        return Arrays.stream(name.split(" "))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));
    }
}

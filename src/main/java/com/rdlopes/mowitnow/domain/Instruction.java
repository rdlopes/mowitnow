package com.rdlopes.mowitnow.domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * An instruction to be executed by the {@link LawnMower}
 */
@Slf4j
public enum Instruction {
    LEFT("G"),
    RIGHT("D"),
    FORWARD("A");

    @NonNull
    private final String label;

    Instruction(String label) {
        this.label = label;
    }

    public static List<Instruction> parseAll(String instructions) {
        log.trace("parseAll called with instructions:{}", instructions);
        if (isEmpty(instructions)) {
            log.warn("empty instructions provided");
            return emptyList();
        }

        return stream(instructions.split("(?!^)"))
                .map(Instruction::of)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    public static Instruction of(String value) {
        log.trace("of called with value:{}", value);
        return stream(Instruction.values())
                .filter(i -> i.label.equals(value))
                .findAny()
                .orElse(null);
    }
}

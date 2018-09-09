package com.rdlopes.mowitnow.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * The representation of an automatic lawn mower.
 */
@Slf4j
@Data
@Builder
public class Mower {

    @NonNull
    private final Integer id;

    @NonNull
    private final List<Instruction> instructions;

    @NonNull
    private Position position;

    public void move(Lawn lawn, Instruction instruction) {
        log.trace("move called with lawn:{}, instruction:{}", lawn, instruction);
        if (lawn != null && instruction != null) {
            Position nextPosition = getPosition().after(instruction);
            if (lawn.contains(nextPosition)) {
                setPosition(nextPosition);
            } else {
                log.debug("position is outside lawn, lawn:{}, position:{}", lawn, nextPosition);
            }
        } else {
            log.warn("one of lawn or instruction was null, lawn:{}, instruction:{}", lawn, instruction);
        }
    }

    public Instruction popNextInstruction() {
        log.trace("popNextInstruction called");
        return getInstructions().isEmpty() ? null :
               getInstructions().remove(0);
    }

    public Position mow(Lawn lawn) {
        log.trace("mow called with lawn:{}", lawn);
        if (lawn != null) {
            Instruction nextInstruction = popNextInstruction();
            while (nextInstruction != null) {
                move(lawn, nextInstruction);
                nextInstruction = popNextInstruction();
            }
        } else {
            log.warn("lawn provided is null");
        }

        log.debug("mow finished, lawn mower at position:{}", getPosition());
        return getPosition();
    }

    /**
     * An instruction to be executed by the {@link Mower}
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
            Instruction.log.trace("parseAll called with instructions:{}", instructions);
            if (isEmpty(instructions)) {
                Instruction.log.warn("empty instructions provided");
                return emptyList();
            }

            return stream(instructions.split("(?!^)"))
                    .map(Instruction::of)
                    .filter(Objects::nonNull)
                    .collect(toList());
        }

        public static Instruction of(String value) {
            Instruction.log.trace("of called with value:{}", value);
            return stream(Instruction.values())
                    .filter(i -> i.label.equals(value))
                    .findAny()
                    .orElse(null);
        }
    }

    @Slf4j
    public enum Orientation {
        NORTH("N"),
        EAST("E"),
        WEST("W"),
        SOUTH("S");

        static {
            NORTH.left = WEST;
            NORTH.right = EAST;
            EAST.left = NORTH;
            EAST.right = SOUTH;
            WEST.left = SOUTH;
            WEST.right = NORTH;
            SOUTH.left = EAST;
            SOUTH.right = WEST;
        }

        private final String label;

        private Orientation left;

        private Orientation right;

        Orientation(String label) {
            this.label = label;
        }

        public static Orientation of(String value) {
            Orientation.log.trace("of called with value:{}", value);
            return stream(Orientation.values())
                         .filter(o -> o.label.equals(value))
                         .findAny()
                         .orElse(null);
        }

        public Orientation left() {
            return left;
        }

        public Orientation right() {
            return right;
        }
    }
}

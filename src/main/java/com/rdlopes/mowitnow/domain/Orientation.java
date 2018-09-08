package com.rdlopes.mowitnow.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

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
        log.trace("of called with value:{}", value);
        return Arrays.stream(Orientation.values())
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

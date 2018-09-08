package com.rdlopes.mowitnow.domain;

import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * A value object referring to the lawn to be mowed.
 */
@Slf4j
@Value(staticConstructor = "of")
public class Lawn {

    @NonNull
    private final Integer width;

    @NonNull
    private final Integer height;

    boolean contains(Position position) {
        log.trace("contains called with position:{}", position);
        return position != null &&
               position.getX() >= 0 &&
               position.getX() < getWidth() &&
               position.getY() >= 0 &&
               position.getY() < getHeight();
    }
}

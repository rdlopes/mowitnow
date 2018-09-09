package com.rdlopes.mowitnow.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A value object referring to the lawn to be mowed.
 */
@Slf4j
@Data
@Builder
public class Lawn {

    @NonNull
    private final int width;

    @NonNull
    private final int height;

    @Singular
    @NonNull
    private final List<Mower> mowers;

    boolean contains(Position position) {
        log.trace("contains called with position:{}", position);
        return position != null &&
               position.getX() >= 0 &&
               position.getX() < getWidth() &&
               position.getY() >= 0 &&
               position.getY() < getHeight();
    }

    public List<Position> mow() {
        return mowers.stream()
                     .sequential()
                     .map(mower -> mower.mow(this))
                     .collect(Collectors.toList());
    }
}

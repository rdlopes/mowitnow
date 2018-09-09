package com.rdlopes.mowitnow.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
}

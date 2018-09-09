package com.rdlopes.mowitnow.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
    private Queue<Instruction> instructions;

    @NonNull
    private Position position;

    public void move(Lawn lawn, Instruction instruction) {
        Position nextPosition = getPosition().after(instruction);
        if (lawn.contains(nextPosition)) {
            setPosition(nextPosition);
        } else {
            log.debug("position is outside lawn, lawn:{}, position:{}", lawn, nextPosition);
        }
    }

    Position mow(Lawn lawn) {
        while (!instructions.isEmpty()) {
            Instruction instruction = instructions.poll();
            move(lawn, instruction);
        }
        return getPosition();
    }

    /**
     * An instruction to be executed by the {@link Mower}
     */
    @Slf4j
    public enum Instruction {
        G, D, A;

        public static List<Instruction> parseAll(String instructions) {
            Instruction.log.trace("parseAll called with instructions:{}", instructions);
            if (isEmpty(instructions)) {
                Instruction.log.warn("empty instructions provided");
                return emptyList();
            }

            return stream(instructions.split("(?!^)"))
                    .map(Instruction::valueOf)
                    .collect(toList());
        }

    }

    @Slf4j
    public enum Orientation {
        N, E, W, S;

        static {
            N.left = W;
            N.right = E;
            E.left = N;
            E.right = S;
            W.left = S;
            W.right = N;
            S.left = E;
            S.right = W;
        }

        Orientation left;

        Orientation right;
    }

}

package com.rdlopes.mowitnow.domain;

import org.junit.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.*;

public class MowerTest {

    @Test
    public void builder_nullInstructions_throws() {
        Throwable throwable = catchThrowable(() -> Mower.builder()
                                                        .position(Position.of(1, 1, Mower.Orientation.N))
                                                        .instructions(null)
                                                        .build());
        assertThat(throwable).isInstanceOf(NullPointerException.class)
                             .hasNoCause()
                             .hasMessage("instructions");
    }

    @Test
    public void builder_nullPosition_throws() {
        Throwable throwable = catchThrowable(() -> Mower.builder()
                                                        .position(null)
                                                        .instructions(new LinkedList<>(Mower.Instruction.parseAll("GDA")))
                                                        .build());
        assertThat(throwable).isInstanceOf(NullPointerException.class)
                             .hasNoCause()
                             .hasMessage("position");
    }
}
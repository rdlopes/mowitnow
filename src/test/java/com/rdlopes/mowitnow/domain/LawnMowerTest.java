package com.rdlopes.mowitnow.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class LawnMowerTest {

    @Test
    public void builder_nullId_throws() {
        Throwable throwable = catchThrowable(() -> LawnMower.builder()
                                                            .id(null)
                                                            .position(Position.of(1, 1, Orientation.NORTH))
                                                            .instructions(Instruction.parseAll("GDA"))
                                                            .build());
        assertThat(throwable).isInstanceOf(NullPointerException.class)
                             .hasNoCause()
                             .hasMessage("id");
    }

    @Test
    public void builder_nullInstructions_throws() {
        Throwable throwable = catchThrowable(() -> LawnMower.builder()
                                                            .id(1)
                                                            .position(Position.of(1, 1, Orientation.NORTH))
                                                            .instructions(null)
                                                            .build());
        assertThat(throwable).isInstanceOf(NullPointerException.class)
                             .hasNoCause()
                             .hasMessage("instructions");
    }

    @Test
    public void builder_nullPosition_throws() {
        Throwable throwable = catchThrowable(() -> LawnMower.builder()
                                                            .id(1)
                                                            .position(null)
                                                            .instructions(Instruction.parseAll("GDA"))
                                                            .build());
        assertThat(throwable).isInstanceOf(NullPointerException.class)
                             .hasNoCause()
                             .hasMessage("position");
    }

    @Test
    public void mow_nullLawn_returnsSamePosition() {
        Position position = Position.of(1, 1, Orientation.NORTH);
        LawnMower lawnMower = LawnMower.builder()
                                       .id(1)
                                       .position(position)
                                       .instructions(Instruction.parseAll("GDA"))
                                       .build();
        assertThat(lawnMower.mow(null)).isEqualTo(position);
    }

    @Test
    public void move_nullLawn_returnsSamePosition() {
        Position position = Position.of(1, 1, Orientation.NORTH);
        LawnMower lawnMower = LawnMower.builder()
                                       .id(1)
                                       .position(position)
                                       .instructions(Instruction.parseAll("GDA"))
                                       .build();
        lawnMower.move(null, lawnMower.popNextInstruction());
        assertThat(lawnMower.getPosition()).isEqualTo(position);
    }

    @Test
    public void move_nullInstruction_returnsSamePosition() {
        Position position = Position.of(1, 1, Orientation.NORTH);
        Lawn lawn = Lawn.of(2, 2);
        LawnMower lawnMower = LawnMower.builder()
                                       .id(1)
                                       .position(position)
                                       .instructions(Instruction.parseAll("GDA"))
                                       .build();
        lawnMower.move(lawn, null);
        assertThat(lawnMower.getPosition()).isEqualTo(position);
    }

    @Test
    public void move_outsideLawn_returnsSamePosition() {
        Position position = Position.of(1, 1, Orientation.NORTH);
        Lawn lawn = Lawn.of(1, 1);
        LawnMower lawnMower = LawnMower.builder()
                                       .id(1)
                                       .position(position)
                                       .instructions(Instruction.parseAll("A"))
                                       .build();
        lawnMower.move(lawn, lawnMower.popNextInstruction());
        assertThat(lawnMower.getPosition()).isEqualTo(position);
    }

}
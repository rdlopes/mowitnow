package com.rdlopes.mowitnow.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class MowerTest {

    @Test
    public void builder_nullId_throws() {
        Throwable throwable = catchThrowable(() -> Mower.builder()
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
        Throwable throwable = catchThrowable(() -> Mower.builder()
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
        Throwable throwable = catchThrowable(() -> Mower.builder()
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
        Mower mower = Mower.builder()
                           .id(1)
                           .position(position)
                           .instructions(Instruction.parseAll("GDA"))
                           .build();
        assertThat(mower.mow(null)).isEqualTo(position);
    }

    @Test
    public void move_nullLawn_returnsSamePosition() {
        Position position = Position.of(1, 1, Orientation.NORTH);
        Mower mower = Mower.builder()
                           .id(1)
                           .position(position)
                           .instructions(Instruction.parseAll("GDA"))
                           .build();
        mower.move(null, mower.popNextInstruction());
        assertThat(mower.getPosition()).isEqualTo(position);
    }

    @Test
    public void move_nullInstruction_returnsSamePosition() {
        Position position = Position.of(1, 1, Orientation.NORTH);
        Lawn lawn = Lawn.of(2, 2);
        Mower mower = Mower.builder()
                           .id(1)
                           .position(position)
                           .instructions(Instruction.parseAll("GDA"))
                           .build();
        mower.move(lawn, null);
        assertThat(mower.getPosition()).isEqualTo(position);
    }

    @Test
    public void move_outsideLawn_returnsSamePosition() {
        Position position = Position.of(1, 1, Orientation.NORTH);
        Lawn lawn = Lawn.of(1, 1);
        Mower mower = Mower.builder()
                           .id(1)
                           .position(position)
                           .instructions(Instruction.parseAll("A"))
                           .build();
        mower.move(lawn, mower.popNextInstruction());
        assertThat(mower.getPosition()).isEqualTo(position);
    }

}
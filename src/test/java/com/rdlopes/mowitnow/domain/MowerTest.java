package com.rdlopes.mowitnow.domain;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.*;

public class MowerTest {
  
  @Test
  public void builder_nullInstructions_throws() {
    assertThatThrownBy(() -> Mower.builder()
                                  .position(Position.of(1, 1, Mower.Orientation.N))
                                  .instructions(null)
                                  .build())
        .isInstanceOf(NullPointerException.class)
        .hasNoCause()
        .hasMessage("instructions is marked non-null but is null");
  }
  
  @Test
  public void builder_nullPosition_throws() {
    assertThatThrownBy(() -> Mower.builder()
                                  .position(null)
                                  .instructions(new LinkedList<>(Mower.Instruction.parseAll("GDA")))
                                  .build()).isInstanceOf(NullPointerException.class)
                                           .hasNoCause()
                                           .hasMessage("position is marked non-null but is null");
  }
}
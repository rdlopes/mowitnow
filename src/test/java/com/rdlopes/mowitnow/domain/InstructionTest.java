package com.rdlopes.mowitnow.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InstructionTest {
  
  @Test
  public void parseAll_emptyString_isEmpty() {
    assertThat(Mower.Instruction.parseAll("")).isEmpty();
  }
  
  @Test
  public void parseAll_nullString_isEmpty() {
    assertThat(Mower.Instruction.parseAll(null)).isEmpty();
  }
}
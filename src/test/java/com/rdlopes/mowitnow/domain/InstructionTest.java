package com.rdlopes.mowitnow.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class InstructionTest {

    @Test
    public void parseAll_emptyString_isEmpty() {
        assertThat(Mower.Instruction.parseAll("")).isEmpty();
    }

    @Test
    public void parseAll_nullString_isEmpty() {
        assertThat(Mower.Instruction.parseAll(null)).isEmpty();
    }

    @Test
    public void of_unknownString_isNull() {
        assertThat(Mower.Instruction.of("unknown")).isNull();
    }

    @Test
    public void of_emptyString_isNull() {
        assertThat(Mower.Instruction.of("")).isNull();
    }

    @Test
    public void of_nullString_isNull() {
        assertThat(Mower.Instruction.of(null)).isNull();
    }
}
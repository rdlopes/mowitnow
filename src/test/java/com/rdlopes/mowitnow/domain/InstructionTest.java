package com.rdlopes.mowitnow.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class InstructionTest {

    @Test
    public void parseAll_emptyString_isEmpty() {
        assertThat(Instruction.parseAll("")).isEmpty();
    }

    @Test
    public void parseAll_nullString_isEmpty() {
        assertThat(Instruction.parseAll(null)).isEmpty();
    }

    @Test
    public void of_unknownString_isNull() {
        assertThat(Instruction.of("unknown")).isNull();
    }

    @Test
    public void of_emptyString_isNull() {
        assertThat(Instruction.of("")).isNull();
    }

    @Test
    public void of_nullString_isNull() {
        assertThat(Instruction.of(null)).isNull();
    }
}
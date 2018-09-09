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
}
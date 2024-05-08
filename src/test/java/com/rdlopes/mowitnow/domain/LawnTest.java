package com.rdlopes.mowitnow.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LawnTest {
  
  @Test
  public void contains_null_isFalse() {
    Lawn lawn = Lawn.builder()
                    .width(5)
                    .height(5)
                    .build();
    assertThat(lawn.contains(null)).isFalse();
  }
}
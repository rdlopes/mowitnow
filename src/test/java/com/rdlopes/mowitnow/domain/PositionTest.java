package com.rdlopes.mowitnow.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PositionTest {
  
  @Test
  public void of_xNull_Throws() {
    assertThatThrownBy(() -> Position.of(null, 1, Mower.Orientation.N))
        .isInstanceOf(NullPointerException.class)
        .hasNoCause()
        .hasMessage("x is marked non-null but is null");
  }
  
  @Test
  public void of_yNull_Throws() {
    assertThatThrownBy(() -> Position.of(1, null, Mower.Orientation.N))
        .isInstanceOf(NullPointerException.class)
        .hasNoCause()
        .hasMessage("y is marked non-null but is null");
  }
  
  @Test
  public void of_orientationNull_Throws() {
    assertThatThrownBy(() -> Position.of(1, 1, null))
        .isInstanceOf(NullPointerException.class)
        .hasNoCause()
        .hasMessage("orientation is marked non-null but is null");
  }
}
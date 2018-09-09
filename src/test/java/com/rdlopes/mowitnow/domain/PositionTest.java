package com.rdlopes.mowitnow.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class PositionTest {

    @Test
    public void of_xNull_Throws() {
        Throwable throwable = catchThrowable(() -> Position.of(null, 1, Mower.Orientation.N));
        assertThat(throwable).isInstanceOf(NullPointerException.class)
                             .hasNoCause()
                             .hasMessage("x");
    }

    @Test
    public void of_yNull_Throws() {
        Throwable throwable = catchThrowable(() -> Position.of(1, null, Mower.Orientation.N));
        assertThat(throwable).isInstanceOf(NullPointerException.class)
                             .hasNoCause()
                             .hasMessage("y");
    }

    @Test
    public void of_orientationNull_Throws() {
        Throwable throwable = catchThrowable(() -> Position.of(1, 1, null));
        assertThat(throwable).isInstanceOf(NullPointerException.class)
                             .hasNoCause()
                             .hasMessage("orientation");
    }
}
package com.rdlopes.mowitnow.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class LawnTest {

    @Test
    public void contains_null_isFalse() {
        Lawn lawn = Lawn.of(5, 5);
        assertThat(lawn.contains(null)).isFalse();
    }

    @Test
    public void of_nullWidth_Throws() {
        Throwable throwable = catchThrowable(() -> Lawn.of(null, 1));
        assertThat(throwable).isInstanceOf(NullPointerException.class)
                             .hasNoCause()
                             .hasMessage("width");
    }

    @Test
    public void of_nullHeight_Throws() {
        Throwable throwable = catchThrowable(() -> Lawn.of(1, null));
        assertThat(throwable).isInstanceOf(NullPointerException.class)
                             .hasNoCause()
                             .hasMessage("height");
    }
}
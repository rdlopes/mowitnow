package com.rdlopes.mowitnow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MowItNowApplicationTests {
  
  @Autowired
  private MowItNowApplication mowItNowApplication;
  
  @Test
  public void contextLoads() {
    assertThat(mowItNowApplication).isNotNull();
  }
  
}

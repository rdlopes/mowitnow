package com.rdlopes.mowitnow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static java.util.Arrays.deepToString;

@Slf4j
@SpringBootApplication
public class MowItNowApplication {
  
  public static void main(String[] args) {
    log.info("main called with args:{}", deepToString(args));
    new SpringApplicationBuilder(MowItNowApplication.class)
        .headless(false)
        .run(args);
  }
}

package com.rdlopes.mowitnow.config;


import com.rdlopes.mowitnow.parser.DescriptionFileParser;
import com.rdlopes.mowitnow.parser.impl.DescriptionFileParserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ParserConfiguration {
  
  @Bean
  public DescriptionFileParser configurationFileParser() {
    return new DescriptionFileParserImpl();
  }
  
}

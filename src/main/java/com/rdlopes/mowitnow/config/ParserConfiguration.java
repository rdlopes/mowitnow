package com.rdlopes.mowitnow.config;


import com.rdlopes.mowitnow.parser.LawnDescriptionFileParser;
import com.rdlopes.mowitnow.parser.impl.LawnDescriptionFileParserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ParserConfiguration {

    @Bean
    public LawnDescriptionFileParser configurationFileParser() {
        return new LawnDescriptionFileParserImpl();
    }

}

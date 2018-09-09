package com.rdlopes.mowitnow.config;

import com.rdlopes.mowitnow.parser.LawnDescriptionFileParser;
import com.rdlopes.mowitnow.processing.MowItNowSketch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import processing.core.PApplet;

@Slf4j
@Configuration
@Profile("sketch")
@EnableConfigurationProperties(SketchProperties.class)
public class SketchConfiguration {

    @Bean
    public ApplicationRunner processingRunner(SketchProperties sketchProperties, LawnDescriptionFileParser lawnDescriptionFileParser) {
        log.trace("processingRunner called with sketchProperties:{}", sketchProperties);
        return args -> {
            MowItNowSketch sketch = null; //buildSketchFromArgs();
            String[] sketchArgs = new String[]{MowItNowSketch.class.getName()};
            PApplet.runSketch(sketchArgs, sketch);
        };
    }
}

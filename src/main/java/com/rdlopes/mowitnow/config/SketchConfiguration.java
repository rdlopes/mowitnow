package com.rdlopes.mowitnow.config;

import com.rdlopes.mowitnow.domain.*;
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
    public MowItNowSketch defaultSketch(SketchProperties sketchProperties) {
        Lawn lawn = Lawn.of(6, 6);
        Mower mower1 = Mower.builder()
                            .id(0)
                            .position(Position.of(1, 2, Mower.Orientation.NORTH))
                            .instructions(Mower.Instruction.parseAll("GAGAGAGAA"))
                            .build();
        Mower mower2 = Mower.builder()
                            .id(1)
                            .position(Position.of(3, 3, Mower.Orientation.EAST))
                            .instructions(Mower.Instruction.parseAll("AADAADADDA"))
                            .build();
        return new MowItNowSketch(sketchProperties, lawn, mower1, mower2);
    }

    @Bean
    public ApplicationRunner processingRunner(SketchProperties sketchProperties, MowItNowSketch defaultSketch) {
        log.trace("processingRunner called with sketchProperties:{}", sketchProperties);
        return args -> {
            String[] sketchArgs = new String[]{MowItNowSketch.class.getName()};
            PApplet.runSketch(sketchArgs, defaultSketch);
        };
    }
}

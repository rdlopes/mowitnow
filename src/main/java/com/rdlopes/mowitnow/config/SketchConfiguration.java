package com.rdlopes.mowitnow.config;

import com.rdlopes.mowitnow.domain.Lawn;
import com.rdlopes.mowitnow.domain.Mower;
import com.rdlopes.mowitnow.domain.Position;
import com.rdlopes.mowitnow.processing.MowItNowSketch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import processing.core.PApplet;

import java.util.Arrays;
import java.util.LinkedList;

@Slf4j
@Configuration
@Profile("sketch")
@EnableConfigurationProperties(SketchProperties.class)
public class SketchConfiguration {

    @Bean
    public MowItNowSketch defaultSketch(SketchProperties sketchProperties) {
        Mower mower1 = Mower.builder()
                            .position(Position.of(1, 2, Mower.Orientation.N))
                            .instructions(new LinkedList<>(Mower.Instruction.parseAll("GAGAGAGAA")))
                            .build();

        Mower mower2 = Mower.builder()
                            .position(Position.of(3, 3, Mower.Orientation.E))
                            .instructions(new LinkedList<>(Mower.Instruction.parseAll("AADAADADDA")))
                            .build();

        Lawn lawn = Lawn.builder()
                        .width(6)
                        .height(6)
                        .mowers(Arrays.asList(mower1, mower2))
                        .build();

        return new MowItNowSketch(sketchProperties, lawn);
    }

    @Bean
    public ApplicationRunner processingRunner(SketchProperties sketchProperties, MowItNowSketch defauSketch) {
        log.trace("processingRunner called with sketchProperties:{}", sketchProperties);
        return args -> {
            log.info("Starting application with args:{}", args);

            String[] sketchArgs = new String[]{MowItNowSketch.class.getName()};
            PApplet.runSketch(sketchArgs, defauSketch);
        };
    }
}

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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.Files.readAllLines;

@Slf4j
@Configuration
@Profile("sketch")
@EnableConfigurationProperties(SketchProperties.class)
public class SketchConfiguration {

    public static final String MOWING_FILE_ARG = "mowing-file";

    @Bean
    public MowItNowSketch defaultSketch(SketchProperties sketchProperties) {
        Lawn lawn = Lawn.of(6, 6);
        LawnMower lawnMower1 = LawnMower.builder()
                                        .id(0)
                                        .position(Position.of(1, 2, Orientation.NORTH))
                                        .instructions(Instruction.parseAll("GAGAGAGAA"))
                                        .build();
        LawnMower lawnMower2 = LawnMower.builder()
                                        .id(1)
                                        .position(Position.of(3, 3, Orientation.EAST))
                                        .instructions(Instruction.parseAll("AADAADADDA"))
                                        .build();
        return new MowItNowSketch(sketchProperties, lawn, lawnMower1, lawnMower2);
    }

    @Bean
    public ApplicationRunner processingRunner(SketchProperties sketchProperties, MowItNowSketch defaultSketch) {
        log.trace("processingRunner called with sketchProperties:{}", sketchProperties);
        return args -> {
            MowItNowSketch sketch = defaultSketch;
            if (args.containsOption(MOWING_FILE_ARG) &&
                !args.getOptionValues(MOWING_FILE_ARG).isEmpty()) {
                Path mowingFilePath = Paths.get(args.getOptionValues(MOWING_FILE_ARG).get(0));
                if (Files.isRegularFile(mowingFilePath) && Files.isReadable(mowingFilePath)) {
                    List<String> lines = Files.readAllLines(mowingFilePath);
                    if (!lines.isEmpty()) {

                    }
                } else {

                }
            }
            String[] sketchArgs = new String[]{MowItNowSketch.class.getName()};
            PApplet.runSketch(sketchArgs, sketch);
        };
    }
}

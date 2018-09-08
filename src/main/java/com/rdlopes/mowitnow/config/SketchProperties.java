package com.rdlopes.mowitnow.config;

import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties("mow-it-now.sketch")
public class SketchProperties {

    /**
     * Holds a list of mower image {@link Resource}s used in sketch.
     *
     * @see Resource
     * @see processing.core.PImage
     */
    private final List<Resource> mowerImages = new ArrayList<>();

    /**
     * Reference to the wild grass image
     */
    private Resource grassWildImage;

    /**
     * Reference to the wild grass image
     */
    private Resource mowerStoppedImage;

    /**
     * Reference to the mown grass image
     */
    private Resource grassMownImage;

    /**
     * Sketch canvas width, expressed in pixels.
     */
    @NonNull
    private Integer width = PApplet.DEFAULT_WIDTH;

    /**
     * Sketch canvas height, expressed in pixels.
     */
    @NonNull
    private Integer height = PApplet.DEFAULT_HEIGHT;

}

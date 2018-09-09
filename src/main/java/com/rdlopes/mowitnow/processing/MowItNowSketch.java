package com.rdlopes.mowitnow.processing;

import com.rdlopes.mowitnow.config.SketchProperties;
import com.rdlopes.mowitnow.domain.Lawn;
import com.rdlopes.mowitnow.domain.Mower;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class MowItNowSketch extends PApplet {

    private final SketchProperties properties;

    private final LawnGrid lawnGrid;

    private final List<MowerItem> mowerItems = new ArrayList<>();

    public MowItNowSketch(SketchProperties properties, Lawn lawn, Mower... mowers) {
        log.trace("MowItNowSketch called with properties:{}", properties);
        this.properties = properties;

        this.lawnGrid = new LawnGrid(this, properties, lawn);
        Arrays.stream(mowers)
              .map(lawnMower -> new MowerItem(this, properties, lawnMower))
              .forEach(mowerItems::add);
    }

    @Override
    public void settings() {
        log.trace("settings called");
        size(this.properties.getWidth(), this.properties.getHeight());
    }

    @Override
    public void setup() {
        log.trace("setup called");
        frameRate(1);

        // setup items
        this.lawnGrid.setup();
        this.mowerItems.forEach(MowerItem::setup);

        // initialize states
        this.mowerItems.forEach(mowerItem -> lawnGrid.setMown(mowerItem.getPosition()));
    }

    @Override
    public void draw() {
        log.trace("draw called");

        // white background
        background(0);

        // draw grid
        lawnGrid.draw();

        // draw mowers
        this.mowerItems.forEach(mowerItem -> mowerItem.draw(lawnGrid));

        // update state
        this.mowerItems.stream()
                       .filter(MowerItem::hasInstructions)
                       .findFirst()
                       .ifPresent(mowerItem -> mowerItem.moveOn(lawnGrid));
    }

    PImage loadImage(Resource resource) {
        try {
            return loadImage(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            log.error("Exception while loading image from resource:{}", resource, e);
            return null;
        }
    }
}

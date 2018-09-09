package com.rdlopes.mowitnow.processing;

import com.rdlopes.mowitnow.config.SketchProperties;
import com.rdlopes.mowitnow.domain.LawnMower;
import com.rdlopes.mowitnow.domain.Position;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import processing.core.PImage;

import static processing.core.PConstants.*;

@Slf4j
class MowerItem {
    private static final float IMAGE_CELL_SCALE = 0.5f;

    private final MowItNowSketch sketch;

    private final SketchProperties properties;

    private final LawnMower lawnMower;

    private PImage image;

    private PImage stoppedImage;

    MowerItem(MowItNowSketch sketch, SketchProperties properties, LawnMower lawnMower) {
        this.sketch = sketch;
        this.properties = properties;
        this.lawnMower = lawnMower;
    }

    void setup() {
        // load image from properties
        image = sketch.loadImage(properties.getMowerImages().get(lawnMower.getId() % properties.getMowerImages().size()));
        stoppedImage = sketch.loadImage(properties.getMowerStoppedImage());
    }

    @NonNull
    public Position getPosition() {
        return lawnMower.getPosition();
    }

    void draw(LawnGrid grid) {
        sketch.pushStyle();
        sketch.imageMode(CENTER);

        float cellSize = grid.cellSizeFull();
        float halfCellSize = cellSize / 2;

        float xStart = grid.cellX(getPosition().getX()) + halfCellSize;
        float yStart = grid.cellY(getPosition().getY()) + halfCellSize;
        float imageSize = cellSize * IMAGE_CELL_SCALE;

        PImage mowerImage = hasInstructions() ? image :
                            stoppedImage;

        sketch.pushMatrix();
        sketch.translate(xStart, yStart);
        sketch.image(mowerImage, 0, 0, imageSize, imageSize);
        sketch.translate(-imageSize, -imageSize);

        sketch.fill(255, 255, 0);
        switch (getPosition().getOrientation()) {
            case NORTH:
                sketch.textAlign(CENTER, TOP);
                sketch.text("N", 0, 0, cellSize, cellSize);
                break;
            case EAST:
                sketch.textAlign(RIGHT, CENTER);
                sketch.text("E", 0, 0, cellSize, cellSize);
                break;
            case WEST:
                sketch.textAlign(LEFT, CENTER);
                sketch.text("W", 0, 0, cellSize, cellSize);
                break;
            case SOUTH:
                sketch.textAlign(CENTER, BOTTOM);
                sketch.text("S", 0, 0, cellSize, cellSize);
                break;
        }
        sketch.popMatrix();
        sketch.popStyle();
    }

    boolean hasInstructions() {
        return !lawnMower.getInstructions().isEmpty();
    }

    void moveOn(LawnGrid lawnGrid) {
        lawnMower.move(lawnGrid.getLawn(), lawnMower.popNextInstruction());
        lawnGrid.setMown(getPosition());
    }
}

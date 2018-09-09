package com.rdlopes.mowitnow.processing;

import com.rdlopes.mowitnow.config.SketchProperties;
import com.rdlopes.mowitnow.domain.Lawn;
import com.rdlopes.mowitnow.domain.Mower;
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

    private LawnItem lawnItem;

    private final Mower mower;

    private int index;

    private PImage image;

    private PImage stoppedImage;

    MowerItem(MowItNowSketch sketch, LawnItem lawnItem, Mower mower, SketchProperties properties, int index) {
        this.sketch = sketch;
        this.properties = properties;
        this.lawnItem = lawnItem;
        this.mower = mower;
        this.index = index;
    }

    void setup() {
        // load image from properties
        image = sketch.loadImage(properties.getMowerImages().get(index % properties.getMowerImages().size()));
        stoppedImage = sketch.loadImage(properties.getMowerStoppedImage());
    }

    @NonNull
    public Position getPosition() {
        return mower.getPosition();
    }

    void draw() {
        sketch.pushStyle();
        sketch.imageMode(CENTER);

        float cellSize = lawnItem.cellSizeFull();
        float halfCellSize = cellSize / 2;

        float xStart = lawnItem.cellX(getPosition().getX()) + halfCellSize;
        float yStart = lawnItem.cellY(getPosition().getY()) + halfCellSize;
        float imageSize = cellSize * IMAGE_CELL_SCALE;

        PImage mowerImage = hasInstructions() ? image :
                            stoppedImage;

        sketch.pushMatrix();
        sketch.translate(xStart, yStart);
        sketch.image(mowerImage, 0, 0, imageSize, imageSize);
        sketch.translate(-imageSize, -imageSize);

        sketch.fill(255, 255, 0);
        switch (getPosition().getOrientation()) {
            case N:
                sketch.textAlign(CENTER, TOP);
                sketch.text("N", 0, 0, cellSize, cellSize);
                break;
            case E:
                sketch.textAlign(RIGHT, CENTER);
                sketch.text("E", 0, 0, cellSize, cellSize);
                break;
            case W:
                sketch.textAlign(LEFT, CENTER);
                sketch.text("W", 0, 0, cellSize, cellSize);
                break;
            case S:
                sketch.textAlign(CENTER, BOTTOM);
                sketch.text("S", 0, 0, cellSize, cellSize);
                break;
        }
        sketch.popMatrix();
        sketch.popStyle();
    }

    boolean hasInstructions() {
        return !mower.getInstructions().isEmpty();
    }

    public void mow(LawnItem lawnItem) {
        mower.move(lawnItem.getLawn(), mower.getInstructions().poll());
        lawnItem.setMown(getPosition());
    }
}

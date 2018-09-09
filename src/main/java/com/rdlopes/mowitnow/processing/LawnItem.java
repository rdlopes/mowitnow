package com.rdlopes.mowitnow.processing;

import com.rdlopes.mowitnow.config.SketchProperties;
import com.rdlopes.mowitnow.domain.Lawn;
import com.rdlopes.mowitnow.domain.Position;
import lombok.extern.slf4j.Slf4j;
import processing.core.PImage;

import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.min;
import static java.util.stream.Collectors.toList;
import static processing.core.PConstants.BOTTOM;
import static processing.core.PConstants.LEFT;

@Slf4j
class LawnItem {

    private static final int CELL_MARGIN = 2;


    private static final int STROKE_WEIGHT = CELL_MARGIN * 2;

    private static final int TEXT_SIZE = 15;

    private final SketchProperties properties;

    private final MowItNowSketch sketch;

    private final boolean[][] cells;

    private final List<MowerItem> mowerItems;

    private final Lawn lawn;

    private final float cellSize;

    private PImage mownGrassImage;

    private PImage wildGrassImage;

    LawnItem(MowItNowSketch sketch, SketchProperties properties, Lawn lawn) {
        this.sketch = sketch;
        this.properties = properties;
        this.mowerItems = IntStream.range(0, lawn.getMowers().size())
                                   .mapToObj(index -> new MowerItem(sketch, this, lawn.getMowers().get(index), properties, index))
                                   .collect(toList());

        this.cells = new boolean[lawn.getWidth()][lawn.getHeight()];
        this.lawn = lawn;
        this.cellSize = min(properties.getWidth() / lawn.getWidth(), properties.getHeight() / lawn.getHeight());
    }

    Lawn getLawn() {
        return lawn;
    }

    void setup() {
        // load images from properties
        wildGrassImage = sketch.loadImage(properties.getGrassWildImage());
        mownGrassImage = sketch.loadImage(properties.getGrassMownImage());

        // setup mower items
        this.mowerItems.forEach(MowerItem::setup);

        // initialize states
        this.mowerItems.forEach(mowerItem -> setMown(mowerItem.getPosition()));
    }

    void draw() {
        sketch.pushStyle();
        sketch.noFill();
        sketch.stroke(0, 100, 0);
        sketch.strokeWeight(STROKE_WEIGHT);
        sketch.textAlign(LEFT, BOTTOM);
        sketch.textSize(TEXT_SIZE);

        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                PImage grassImage = getGrassImageAt(x, y);
                String coordinates = String.format("%d:%d", x, y);
                float xStart = cellX(x);
                float yStart = cellY(y);
                float xInsideStart = cellXInside(x);
                float yInsideStart = cellYInside(y);
                float sizeFull = cellSizeFull();
                float sizeInside = cellSizeInside();

                sketch.rect(xStart, yStart, sizeFull, sizeFull);
                sketch.image(grassImage, xInsideStart, yInsideStart, sizeInside, sizeInside);
                sketch.text(coordinates, xInsideStart, yInsideStart, sizeInside, sizeInside);
            }
        }
        sketch.popStyle();

        // draw mower items
        this.mowerItems.forEach(MowerItem::draw);
    }

    void setMown(Position position) {
        cells[position.getX()][position.getY()] = true;
    }

    private PImage getGrassImageAt(int x, int y) {
        return cells[x][y] ? mownGrassImage : wildGrassImage;
    }

    float cellX(int x) {
        return x * cellSize;
    }

    private float cellXInside(int x) {
        return cellX(x) + CELL_MARGIN;
    }

    float cellY(int y) {
        return (lawn.getHeight() - y - 1) * cellSize;
    }

    private float cellYInside(int y) {
        return cellY(y) + CELL_MARGIN;
    }

    float cellSizeFull() {
        return cellSize;
    }

    private float cellSizeInside() {
        return cellSize - CELL_MARGIN;
    }

    void mow() {
        mowerItems.stream()
                  .filter(MowerItem::hasInstructions)
                  .findFirst()
                  .ifPresent(mowerItem -> mowerItem.mow(this));
    }
}

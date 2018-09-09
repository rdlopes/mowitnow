package com.rdlopes.mowitnow.processing;

import com.rdlopes.mowitnow.config.SketchProperties;
import com.rdlopes.mowitnow.domain.Lawn;
import com.rdlopes.mowitnow.domain.Position;
import lombok.extern.slf4j.Slf4j;
import processing.core.PImage;

import static java.lang.Math.min;
import static processing.core.PConstants.BOTTOM;
import static processing.core.PConstants.LEFT;

@Slf4j
class LawnGrid {

    private static final int CELL_MARGIN = 2;


    private static final int STROKE_WEIGHT = CELL_MARGIN * 2;

    private static final int TEXT_SIZE = 15;

    private final SketchProperties properties;

    private final MowItNowSketch sketch;

    private final boolean[][] cells;

    Lawn getLawn() {
        return lawn;
    }

    private final Lawn lawn;

    private PImage mownGrassImage;

    private PImage wildGrassImage;

    private final float cellSize;

    LawnGrid(MowItNowSketch sketch, SketchProperties properties, Lawn lawn) {
        this.sketch = sketch;
        this.properties = properties;

        this.cells = new boolean[lawn.getWidth()][lawn.getHeight()];
        this.lawn = lawn;
        this.cellSize = min(properties.getWidth() / lawn.getWidth(), properties.getHeight() / lawn.getHeight());
    }

    void setup() {
        // load images from properties
        wildGrassImage = sketch.loadImage(properties.getGrassWildImage());
        mownGrassImage = sketch.loadImage(properties.getGrassMownImage());
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
}

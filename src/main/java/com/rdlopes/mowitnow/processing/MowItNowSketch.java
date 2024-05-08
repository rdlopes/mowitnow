package com.rdlopes.mowitnow.processing;

import com.rdlopes.mowitnow.config.SketchProperties;
import com.rdlopes.mowitnow.domain.Lawn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;

@Slf4j
public class MowItNowSketch extends PApplet {
  
  private final SketchProperties properties;
  
  private final LawnItem lawnItem;
  
  public MowItNowSketch(SketchProperties properties, Lawn lawn) {
    log.trace("MowItNowSketch called with properties:{}", properties);
    this.properties = properties;
    
    this.lawnItem = new LawnItem(this, properties, lawn);
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
    
    this.lawnItem.setup();
  }
  
  @Override
  public void draw() {
    log.trace("draw called");
    background(0);
    lawnItem.draw();
    lawnItem.mow();
  }
  
  PImage loadImage(Resource resource) {
    try {
      return loadImage(resource.getFile()
                               .getAbsolutePath());
    } catch (IOException e) {
      log.error("Exception while loading image from resource:{}", resource, e);
      return null;
    }
  }
}

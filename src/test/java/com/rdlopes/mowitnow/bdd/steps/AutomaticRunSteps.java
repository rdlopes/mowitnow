package com.rdlopes.mowitnow.bdd.steps;

import com.rdlopes.mowitnow.bdd.spring.BaseSpringSteps;
import com.rdlopes.mowitnow.domain.Lawn;
import com.rdlopes.mowitnow.domain.Mower;
import com.rdlopes.mowitnow.domain.Position;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AutomaticRunSteps {
  
  private Lawn.LawnBuilder lawnBuilder;
  
  private List<Position> finalPositions;
  
  @Etantdonné("^une pelouse de largeur (\\d+) et de hauteur (\\d+)$")
  public void aLawnOfWidthAndHeight(int width, int height) {
    this.lawnBuilder = Lawn.builder()
                           .width(width)
                           .height(height);
  }
  
  @Et("^une tondeuse initialement en position (\\d+), (\\d+), ([NEWS]) qui doit exécuter les instructions ([GDA]+)$")
  public void aLawnMowerWithInitialPositionRunningInstructions(Integer x, Integer y, String orientationLabel, String instructions) {
    Mower.Orientation orientation = Mower.Orientation.valueOf(orientationLabel);
    Position position = Position.of(x, y, orientation);
    Mower mower = Mower.builder()
                       .position(position)
                       .instructions(new LinkedList<>(Mower.Instruction.parseAll(instructions)))
                       .build();
    lawnBuilder.mower(mower);
  }
  
  @Quand("^les instructions sont exécutées$")
  public void instructionsAreExecuted() {
    Lawn lawn = lawnBuilder.build();
    this.finalPositions = lawn.mow();
  }
  
  @Alors("^la position finale de la tondeuse (\\d+) doit être (\\d+) (\\d+) ([NEWS])$")
  public void finalMowerPositionMustBe(Integer id, Integer x, Integer y, String orientationLabel) {
    Mower.Orientation expectedOrientation = Mower.Orientation.valueOf(orientationLabel);
    Position expectedPosition = Position.of(x, y, expectedOrientation);
    Position finalPosition = finalPositions.get(id - 1);
    assertThat(finalPosition).isEqualTo(expectedPosition);
  }
}

package com.rdlopes.mowitnow.bdd.steps;

import com.rdlopes.mowitnow.domain.*;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Et;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Quand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.*;

public class AutomaticRunSteps {

    private final List<Mower> mowers = new ArrayList<>();

    private Lawn lawn;

    private Map<Integer, Position> finalPositions;

    @Etantdonné("^une pelouse de largeur (\\d+) et de hauteur (\\d+)$")
    public void aLawnOfWidthAndHeight(int width, int height) {
        this.lawn = Lawn.of(width, height);
    }

    @Et("^une tondeuse (\\d+) intialement en position (\\d+), (\\d+), ([NEWS]) qui doit exécuter les instructions ([GDA]+)$")
    public void aLawnMowerWithInitialPositionRunningInstructions(Integer id, Integer x, Integer y, String orientationLabel, String instructions) {
        Mower.Orientation orientation = Mower.Orientation.valueOf(orientationLabel);
        Position position = Position.of(x, y, orientation);
        List<Mower.Instruction> instructionList = Mower.Instruction.parseAll(instructions);
        Mower mower = Mower.builder()
                           .id(id)
                           .position(position)
                           .instructions(instructionList)
                           .build();
        this.mowers.add(mower);
    }

    @Quand("^les instructions sont exécutées$")
    public void instructionsAreExecuted() {
        this.finalPositions = this.mowers.stream()
                                         .sequential()
                                         .collect(toMap(Mower::getId,
                                                            lawnMower -> lawnMower.mow(lawn)));
    }

    @Alors("^la position finale de la tondeuse (\\d+) doit être (\\d+) (\\d+) ([NEWS])$")
    public void finalMowerPositionMustBe(Integer id, Integer x, Integer y, String orientationLabel) {
        Mower.Orientation orientation = Mower.Orientation.valueOf(orientationLabel);
        Position expectedPosition = Position.of(x, y, orientation);
        Position finalPosition = finalPositions.get(id);
        assertThat(finalPosition).isEqualTo(expectedPosition);
    }
}

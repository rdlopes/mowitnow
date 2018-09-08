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

    private final List<LawnMower> lawnMowers = new ArrayList<>();

    private Lawn lawn;

    private Map<Integer, Position> finalPositions;

    @Etantdonné("^une pelouse de largeur (\\d+) et de hauteur (\\d+)$")
    public void aLawnOfWidthAndHeight(int width, int height) throws Throwable {
        this.lawn = Lawn.of(width, height);
    }

    @Et("^une tondeuse (\\d+) intialement en position (\\d+), (\\d+), ([NEWS]) qui doit exécuter les instructions ([GDA]+)$")
    public void aLawnMowerWithInitialPositionRunningInstructions(Integer id, Integer x, Integer y, String orientationLabel, String instructions) throws Throwable {
        Orientation orientation = Orientation.of(orientationLabel);
        Position position = Position.of(x, y, orientation);
        List<Instruction> instructionList = Instruction.parseAll(instructions);
        LawnMower lawnMower = LawnMower.builder()
                                       .id(id)
                                       .position(position)
                                       .instructions(instructionList)
                                       .build();
        this.lawnMowers.add(lawnMower);
    }

    @Quand("^les instructions sont exécutées$")
    public void instructionsAreExecuted() throws Throwable {
        this.finalPositions = this.lawnMowers.stream()
                                             .sequential()
                                             .collect(toMap(LawnMower::getId,
                                                            lawnMower -> lawnMower.mow(lawn)));
    }

    @Alors("^la position finale de la tondeuse (\\d+) doit être (\\d+) (\\d+) ([NEWS])$")
    public void finalMowerPositionMustBe(Integer id, Integer x, Integer y, String orientationLabel) throws Throwable {
        Orientation orientation = Orientation.of(orientationLabel);
        Position expectedPosition = Position.of(x, y, orientation);
        Position finalPosition = finalPositions.get(id);
        assertThat(finalPosition).isEqualTo(expectedPosition);
    }
}

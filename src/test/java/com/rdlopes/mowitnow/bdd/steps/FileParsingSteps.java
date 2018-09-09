package com.rdlopes.mowitnow.bdd.steps;

import com.rdlopes.mowitnow.bdd.spring.BaseSpringSteps;
import com.rdlopes.mowitnow.domain.Lawn;
import com.rdlopes.mowitnow.domain.Mower;
import com.rdlopes.mowitnow.domain.Position;
import com.rdlopes.mowitnow.parser.DescriptionFileParser;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Et;
import cucumber.api.java.fr.Etantdonné;
import cucumber.api.java.fr.Quand;
import org.assertj.core.data.Index;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.*;

public class FileParsingSteps extends BaseSpringSteps {

    @Autowired
    private DescriptionFileParser descriptionFileParser;

    private File descriptionFile;

    private Lawn lawn;

    @Etantdonné("^un fichier de description de la pelouse situé dans (.+)$")
    public void aDescriptionFileLocatedAt(String fileLocation) {
        this.descriptionFile = new File(fileLocation);
    }

    @Quand("^le fichier est lu par l'application$")
    public void fileIsParsedByApplication() throws Throwable {
        descriptionFileParser.parse(descriptionFile);
        this.lawn = descriptionFileParser.getLawn();
    }

    @Alors("^on doit retrouver une pelouse de taille (\\d+)x(\\d+)$")
    public void weMustFindALawnOfSize(int width, int height) {
        assertThat(lawn).isNotNull()
                        .hasFieldOrPropertyWithValue("width", width)
                        .hasFieldOrPropertyWithValue("height", height);
    }

    @Et("^une tondeuse à l'indexe (\\d+) en position (\\d+), (\\d+), ([NEWS]) qui doit exécuter les instructions ([GDA]+)$")
    public void aLawnMowerWithPositionRunningInstructions(int index, int x, int y, String orientationCode, String instructions) {
        Mower.Orientation orientation = Mower.Orientation.valueOf(orientationCode);
        Position position = Position.of(x, y, orientation);
        Mower mower = Mower.builder()
                           .position(position)
                           .instructions(new LinkedList<>(Mower.Instruction.parseAll(instructions)))
                           .build();

        assertThat(lawn.getMowers()).isNotNull()
                                    .contains(mower, Index.atIndex(index));
    }

}

package com.rdlopes.mowitnow.parser.impl;

import com.rdlopes.mowitnow.domain.Lawn;
import com.rdlopes.mowitnow.domain.Mower;
import com.rdlopes.mowitnow.domain.Position;
import com.rdlopes.mowitnow.parser.DescriptionFileParser;
import com.rdlopes.mowitnow.parser.ParsingException;
import com.rdlopes.mowitnow.parser.fsm.ParserContext;
import com.rdlopes.mowitnow.parser.fsm.State;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import static java.nio.file.Files.readAllLines;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Data
public class DescriptionFileParserImpl implements DescriptionFileParser, ParserContext {

    @Getter
    @NonNull
    private State state;

    @NonNull
    private Queue<String> content = new LinkedList<>();

    private Lawn.LawnBuilder lawnBuilder = null;

    private Mower.MowerBuilder mowerBuilder = null;

    public DescriptionFileParserImpl() {
        this.state = States.READING_LAWN_DIMENSIONS;
    }

    @Override
    public void parse(File descriptionFile) throws ParsingException {
        try {
            readAllLines(descriptionFile.toPath()).forEach(content::offer);

        } catch (IOException e) {
            throw new ParsingException(MessageFormat.format("Could not read description file {0}", descriptionFile));
        }

        while (state.test(this)) {
            this.state = state.process(this);
        }

        if (!isInTerminalState()) {
            throw new ParsingException(MessageFormat.format("Parser is not in a terminal state, state:{0}", getState()));
        }
    }

    @Override
    public Lawn getLawn() {
        return Optional.ofNullable(lawnBuilder)
                       .map(Lawn.LawnBuilder::build)
                       .orElse(null);
    }

    private boolean isInTerminalState() {
        return getState().equals(States.END);
    }

    @Override
    public boolean hasMoreLines() {
        return !content.isEmpty() && hasText(content.peek());
    }

    @Override
    public String getNextLine() {
        return content.poll();
    }

    @Override
    public void setLawnDimension(int width, int height) {
        lawnBuilder = Lawn.builder()
                          .width(width)
                          .height(height);
    }

    @Override
    public void setMowerCoordinates(Position position) {
        mowerBuilder = Mower.builder()
                            .position(position);
    }

    @Override
    public void setMowerInstructions(Queue<Mower.Instruction> instructions) {
        mowerBuilder.instructions(instructions);
        lawnBuilder.mower(mowerBuilder.build());
    }
}

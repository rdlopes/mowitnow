package com.rdlopes.mowitnow.parser.fsm;

import com.rdlopes.mowitnow.domain.Mower;
import com.rdlopes.mowitnow.domain.Position;

import java.util.Queue;

public interface Context {

    boolean hasMoreLines();

    String getNextLine();

    void setLawnDimension(int width, int height);

    void setMowerCoordinates(Position position);

    void setMowerInstructions(Queue<Mower.Instruction> instructions);
}

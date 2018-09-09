package com.rdlopes.mowitnow.parser.impl;

import com.rdlopes.mowitnow.domain.Mower;
import com.rdlopes.mowitnow.domain.Position;
import com.rdlopes.mowitnow.parser.ParsingException;
import com.rdlopes.mowitnow.parser.fsm.ParserContext;
import com.rdlopes.mowitnow.parser.fsm.State;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.toCollection;

@Slf4j
enum States implements State {
    READING_LAWN_DIMENSIONS {
        @Override
        public boolean test(ParserContext parserContext) {
            return parserContext.hasMoreLines();
        }

        @Override
        public State process(ParserContext parserContext) throws ParsingException {
            String contentLine = parserContext.getNextLine();
            try (Scanner scanner = new Scanner(contentLine)) {
                int x;
                if (scanner.hasNextInt()) {
                    x = scanner.nextInt();

                } else {
                    throw new ParsingException("Could not read x value");
                }

                int y;
                if (scanner.hasNextInt()) {
                    y = scanner.nextInt();
                } else {
                    throw new ParsingException("Could not read y value");
                }

                if (x >= 0 && y >= 0) {
                    parserContext.setLawnDimension(x + 1, y + 1);
                    return READING_MOWER_COORDINATES;

                } else {
                    throw new ParsingException(format("Cannot accept values x:{0}, y:{1}", x, y));
                }

            } catch (Exception e) {
                throw new ParsingException(format(FAILED_SCANNING_LINE_MESSAGE, contentLine), e);
            }
        }
    },
    READING_MOWER_COORDINATES {
        @Override
        public boolean test(ParserContext parserContext) {
            return parserContext.hasMoreLines();
        }

        @Override
        public State process(ParserContext parserContext) throws ParsingException {
            String contentLine = parserContext.getNextLine();
            try (Scanner scanner = new Scanner(contentLine)) {
                int x;
                if (scanner.hasNextInt()) {
                    x = scanner.nextInt();

                } else {
                    throw new ParsingException("Could not read x value");
                }

                int y;
                if (scanner.hasNextInt()) {
                    y = scanner.nextInt();
                } else {
                    throw new ParsingException("Could not read y value");
                }

                String orientationCode;
                String pattern = EnumSet.allOf(Mower.Orientation.class)
                                        .stream()
                                        .map(Enum::name)
                                        .collect(Collectors.joining("", "[", "]"));
                if (scanner.hasNext(pattern)) {
                    orientationCode = scanner.next();

                } else {
                    throw new ParsingException("Could not read orientation value");
                }

                if (x >= 0 && y >= 0 && orientationCode != null) {
                    Mower.Orientation orientation = Mower.Orientation.valueOf(orientationCode);
                    Position position = Position.of(x, y, orientation);
                    parserContext.setMowerCoordinates(position);
                    return READING_MOWER_INSTRUCTIONS;

                } else {
                    throw new ParsingException(format("Cannot accept values x:{0}, y:{1}, orientationCode:{2}", x, y, orientationCode));
                }

            } catch (Exception e) {
                throw new ParsingException(format(FAILED_SCANNING_LINE_MESSAGE, contentLine), e);
            }
        }
    },
    READING_MOWER_INSTRUCTIONS {
        @Override
        public boolean test(ParserContext parserContext) {
            return parserContext.hasMoreLines();
        }

        @Override
        public State process(ParserContext parserContext) throws ParsingException {
            String contentLine = parserContext.getNextLine();
            try (Scanner scanner = new Scanner(contentLine)) {
                String instructionCodes;
                String pattern = EnumSet.allOf(Mower.Instruction.class)
                                        .stream()
                                        .map(Enum::name)
                                        .collect(Collectors.joining("", "[", "]+"));
                if (scanner.hasNext(pattern)) {
                    instructionCodes = scanner.next();

                } else {
                    throw new ParsingException("Could not read instructions value");
                }

                if (instructionCodes != null) {
                    Queue<Mower.Instruction> instructions = Arrays.stream(instructionCodes.split("(?!^)"))
                                                                  .map(Mower.Instruction::valueOf)
                                                                  .collect(toCollection(LinkedList::new));

                    parserContext.setMowerInstructions(instructions);
                    return parserContext.hasMoreLines() ? READING_MOWER_COORDINATES : END;

                } else {
                    throw new ParsingException("Cannot accept null instructions");
                }

            } catch (Exception e) {
                throw new ParsingException(format(FAILED_SCANNING_LINE_MESSAGE, contentLine), e);
            }
        }
    },
    END {
        @Override
        public boolean test(ParserContext parserContext) {
            return false;
        }

        @Override
        public State process(ParserContext parserContext) {
            return this;
        }
    };

    private static final String FAILED_SCANNING_LINE_MESSAGE = "Failed scanning line {0}";
}

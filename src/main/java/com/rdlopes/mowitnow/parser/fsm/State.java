package com.rdlopes.mowitnow.parser.fsm;

import com.rdlopes.mowitnow.parser.ParsingException;

import java.util.function.Predicate;

public interface State extends Predicate<ParserContext> {
  State process(ParserContext parserContext) throws ParsingException;
}

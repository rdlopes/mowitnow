package com.rdlopes.mowitnow.parser.fsm;

import com.rdlopes.mowitnow.parser.ParsingException;

import java.util.function.Predicate;

public interface State extends Predicate<Context> {
    State process(Context context) throws ParsingException;
}

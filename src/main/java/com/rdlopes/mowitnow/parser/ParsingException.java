package com.rdlopes.mowitnow.parser;

public class ParsingException extends Exception {

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParsingException(String message) {
        super(message);
    }
}

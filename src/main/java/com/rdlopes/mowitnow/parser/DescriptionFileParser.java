package com.rdlopes.mowitnow.parser;

import com.rdlopes.mowitnow.domain.Lawn;

import java.io.File;

public interface DescriptionFileParser {
  void parse(File descriptionFile) throws ParsingException;
  
  Lawn getLawn();
}

package com.rdlopes.mowitnow.domain;

import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value(staticConstructor = "of")
public class Position {
  
  @NonNull
  Integer x;
  
  @NonNull
  Integer y;
  
  @NonNull
  Mower.Orientation orientation;
  
  Position after(Mower.Instruction instruction) {
    Position.log.trace("after called with instruction:{}", instruction);
    switch (instruction) {
      case G:
        return Position.of(getX(), getY(), getOrientation().left);
      case D:
        return Position.of(getX(), getY(), getOrientation().right);
      case A:
        return forward();
      default:
        return this;
    }
  }
  
  private Position forward() {
    Position.log.trace("forward called");
    switch (getOrientation()) {
      case N:
        return Position.of(getX(), getY() + 1, getOrientation());
      case E:
        return Position.of(getX() + 1, getY(), getOrientation());
      case W:
        return Position.of(getX() - 1, getY(), getOrientation());
      case S:
        return Position.of(getX(), getY() - 1, getOrientation());
      default:
        return this;
    }
  }
}

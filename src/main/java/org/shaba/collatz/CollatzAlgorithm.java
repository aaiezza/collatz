package org.shaba.collatz;

import java.math.BigInteger;
import one.util.streamex.StreamEx;

@lombok.Data
public class CollatzAlgorithm {

  public CollatzSequence calculate(final BigInteger n) {
    final var sequence = new CollatzSequence(n);

    return StreamEx.of(sequence).dropWhile(CollatzSequence::hasNext).findFirst().orElse(sequence);
  }
}

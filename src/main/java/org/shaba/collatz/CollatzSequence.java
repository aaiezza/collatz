package org.shaba.collatz;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static java.math.BigInteger.ZERO;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CollatzSequence implements Iterator<CollatzSequence> {
  private final Deque<BigInteger> value;

  public CollatzSequence(final BigInteger start) {
    value = new ArrayDeque<>();
    value.push(start);
  }

  private CollatzSequence(final Deque<BigInteger> value, final BigInteger next) {
    this.value = value;
    this.value.push(next);
  }

  public BigInteger getStartingNumber() {
    return value.getLast();
  }

  public BigInteger getHighestNumber() {
    return value.parallelStream().reduce(BigInteger::max).orElseThrow();
  }

  public int size() {
    return value.size();
  }

  @Override
  public boolean hasNext() {
    return !value.peek().equals(ONE);
  }

  @Override
  public CollatzSequence next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }

    final BigInteger n = value.peek();

    return new CollatzSequence(
        value,
        n.mod(TWO).equals(ZERO) ? n.divide(TWO) : n.multiply(BigInteger.valueOf(3L)).add(ONE));
  }

  @Override
  public String toString() {
    return String.format(
        "Starting: %s, Highest Number: %s, Iterations: %d",
        getStartingNumber(), getHighestNumber(), size());
  }
}

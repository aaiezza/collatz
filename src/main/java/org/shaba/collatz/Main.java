package org.shaba.collatz;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;

@Slf4j
public class Main {
  public static void main(final String... args) {
    log.info("Starting Collatz traversal");

    final var collatz = new CollatzAlgorithm();

    final AtomicReference<CollatzSequence> highestNumberReached =
        new AtomicReference<>(new CollatzSequence(ONE));
    final AtomicReference<CollatzSequence> highestIterations =
        new AtomicReference<>(new CollatzSequence(ONE));
    final AtomicReference<BigInteger> counter = new AtomicReference<>(ONE);

    final var start = new BigInteger(args[0]);
    final var max = new BigInteger(args.length < 2 ? args[0] : args[1]);

    final var stopwatch = Stopwatch.createStarted();

    StreamEx.iterate(start, ONE::add)
        .takeWhileInclusive(i -> max.subtract(i).signum() > 0)
        .parallel()
        .map(collatz::calculate)
        .peek(seq -> counter.updateAndGet(ONE::add))
        .peek(
            seq ->
                highestIterations.updateAndGet(
                    high -> {
                      if (high.size() <= seq.size()) {
                        log.info("     iterations - {}", seq);
                        return seq;
                      } else return high;
                    }))
        .peek(
            seq -> {
              if (counter.getAcquire().mod(BigInteger.valueOf(1000000)).equals(ZERO))
                log.info("  On: {} [{}]", seq.getStartingNumber(), counter.getAcquire());
            })
        .forEach(
            seq ->
                highestNumberReached.updateAndGet(
                    high -> {
                      if (high.getHighestNumber().subtract(seq.getHighestNumber()).signum() < 1) {
                        log.info("highest reached - {}", seq);
                        return seq;
                      } else {
                        return high;
                      }
                    }));

    log.info(" ~~~ Finished in: {}", stopwatch.stop());

    log.info(highestIterations.get().toString());
    log.info(highestNumberReached.get().toString());
  }
}

package org.shaba.collatz;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollatzAlgorithmTest {
  private CollatzAlgorithm subject;

  @BeforeEach
  void setUp() {
    subject = new CollatzAlgorithm();
  }

  @Test
  void shouldWorkForOneNumber() {
    final CollatzSequence response =
        subject.calculate(
            new BigInteger(
                "123456789123456789239234567898765434567887654345567822345987654345678908765432345678909876543234567337203685478758090"));

    System.out.println(response);
    assertThat(response).isNotNull();
  }
}

package org.andersen.homework.util;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Randomizer {

  public static String getRandomString(int length) {
    StringBuilder stringBuilder = new StringBuilder();
    IntStream.range(0, length).forEach(n -> stringBuilder.append(getRandomInt(0, 9)));
    return stringBuilder.toString();
  }

  public static int getRandomInt(int min, int max) {
    return ThreadLocalRandom.current().nextInt(min, max + 1);
  }
}

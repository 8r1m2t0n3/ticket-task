package org.andersen.homework.util;

import java.util.Random;
import java.util.stream.IntStream;

public class RandomizerUtil {

  private static final Random RANDOM = new Random();

  public static String getRandomString(int length) {
    StringBuilder stringBuilder = new StringBuilder();
    IntStream.range(0, length).forEach(n -> stringBuilder.append(getRandomInt(0, 9)));
    return stringBuilder.toString();
  }

  public static int getRandomInt(int min, int max) {
    return RANDOM.nextInt(min, max + 1);
  }

  public static <E extends Enum<E>> E getRandomFromEnum(Class<E> enumClass) {
    E[] enumConstants = enumClass.getEnumConstants();
    return enumConstants[RANDOM.nextInt(enumConstants.length)];
  }
}

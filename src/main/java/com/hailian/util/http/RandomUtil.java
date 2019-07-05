package com.hailian.util.http;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil
{
  public static final String BASE_NUMBER = "0123456789";
  public static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
  public static final String BASE_CHAR_NUMBER = "abcdefghijklmnopqrstuvwxyz0123456789";

  public static ThreadLocalRandom getRandom()
  {
    return ThreadLocalRandom.current();
  }


  public static int randomInt(int min, int max)
  {
    return getRandom().nextInt(min, max);
  }

  public static int randomInt()
  {
    return getRandom().nextInt();
  }

  public static int randomInt(int limit)
  {
    return getRandom().nextInt(limit);
  }

  public static long randomLong(long min, long max)
  {
    return getRandom().nextLong(min, max);
  }

  public static long randomLong()
  {
    return getRandom().nextLong();
  }

  public static long randomLong(long limit)
  {
    return getRandom().nextLong(limit);
  }

  public static double randomDouble(double min, double max)
  {
    return getRandom().nextDouble(min, max);
  }


  public static byte[] randomBytes(int length)
  {
    byte[] bytes = new byte[length];
    getRandom().nextBytes(bytes);
    return bytes;
  }

  public static <T> T randomEle(List<T> list)
  {
    return randomEle(list, list.size());
  }

  public static <T> T randomEle(List<T> list, int limit)
  {
    return list.get(randomInt(limit));
  }

  public static <T> T randomEle(T[] array)
  {
    return randomEle(array, array.length);
  }

  public static <T> T randomEle(T[] array, int limit)
  {
    return array[randomInt(limit)];
  }

  public static <T> List<T> randomEles(List<T> list, int count)
  {
    List result = new ArrayList(count);
    int limit = list.size();
    while (result.size() < count) {
      result.add(randomEle(list, limit));
    }

    return result;
  }

  public static <T> Set<T> randomEleSet(Collection<T> collection, int count)
  {
    ArrayList source = new ArrayList(new HashSet(collection));
    if (count > source.size()) {
      throw new IllegalArgumentException("Count is larger than collection distinct size !");
    }

    HashSet result = new HashSet(count);
    int limit = collection.size();
    while (result.size() < count) {
      result.add(randomEle(source, limit));
    }

    return result;
  }

  public static String randomString(int length)
  {
    return randomString("abcdefghijklmnopqrstuvwxyz0123456789", length);
  }

  public static String randomStringUpper(int length)
  {
    return randomString("abcdefghijklmnopqrstuvwxyz0123456789", length).toUpperCase();
  }

  public static String randomNumbers(int length)
  {
    return randomString("0123456789", length);
  }

  public static String randomString(String baseString, int length)
  {
    StringBuilder sb = new StringBuilder();

    if (length < 1) {
      length = 1;
    }
    int baseLength = baseString.length();
    for (int i = 0; i < length; i++) {
      int number = getRandom().nextInt(baseLength);
      sb.append(baseString.charAt(number));
    }
    return sb.toString();
  }

  public static int randomNumber()
  {
    return randomChar("0123456789");
  }

  public static char randomChar()
  {
    return randomChar("abcdefghijklmnopqrstuvwxyz0123456789");
  }

  public static char randomChar(String baseString)
  {
    return baseString.charAt(getRandom().nextInt(baseString.length()));
  }

  public static Color randomColor()
  {
    Random random = getRandom();
    return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
  }


  @Deprecated
  public static String randomUUID()
  {
    return UUID.randomUUID().toString();
  }

}
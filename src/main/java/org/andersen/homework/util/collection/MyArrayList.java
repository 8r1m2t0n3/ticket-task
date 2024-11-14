package org.andersen.homework.util.collection;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class MyArrayList<E> {

  private static final Object[] DEFAULT_EMPTY_CAPACITY_ARRAY = {};

  private Object[] array;

  private int capacity;

  private int entriesCount = 0;

  public MyArrayList() {
    this.array = DEFAULT_EMPTY_CAPACITY_ARRAY;
    this.capacity = 0;
  }

  public MyArrayList(int initialCapacity) {
    if (initialCapacity > 0) {
      this.array = new Object[initialCapacity];
    } else if (initialCapacity == 0) {
      this.array = DEFAULT_EMPTY_CAPACITY_ARRAY;
    } else {
      throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
    }

    this.capacity = initialCapacity;
  }

  @SuppressWarnings("unchecked")
  public E get(int index) {
    Objects.checkIndex(index, entriesCount);
    if (index > entriesCount - 1) {
      return null;
    }
    return (E) array[index];
  }

  public void add(E element) {
    if (entriesCount == capacity) {
      array = Arrays.copyOf(array, entriesCount + 1);
      capacity++;
    }
    array[entriesCount] = element;
    entriesCount++;
  }

  public E remove(int index) {
    Objects.checkIndex(index, entriesCount);
    @SuppressWarnings("unchecked") E deletedElement = (E) array[index];
    if (index != capacity) {
      IntStream.range(index, entriesCount - 1)
          .forEach(i -> array[i] = array[i + 1]);
    }
    entriesCount--;

    return deletedElement;
  }

  public void trimToSize() {
    array = Arrays.copyOf(array, entriesCount);
    capacity = entriesCount;
  }

  public Integer size() {
    return entriesCount;
  }

  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder();
    buffer.append("[");
    for (int i = 0; i < entriesCount; i++) {
      buffer.append(array[i]);
      if (i != entriesCount - 1) {
        buffer.append(", ");
      }
    }
    buffer.append("]");
    return buffer.toString();
  }
}

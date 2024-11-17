package org.andersen.homework.util.collection;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyArrayList<E> {

  private static final Object[] DEFAULT_EMPTY_CAPACITY_ARRAY = {};

  private Object[] array;

  private int capacity;

  private int elementsCount = 0;

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
    Objects.checkIndex(index, elementsCount);
    if (index > elementsCount - 1) {
      return null;
    }
    return (E) array[index];
  }

  public void add(E element) {
    if (elementsCount == capacity) {
      array = Arrays.copyOf(array, elementsCount + 1);
      capacity++;
    }
    array[elementsCount] = element;
    elementsCount++;
  }

  public E remove(int index) {
    Objects.checkIndex(index, elementsCount);
    @SuppressWarnings("unchecked") E deletedElement = (E) array[index];
    if (index != capacity) {
      IntStream.range(index, elementsCount - 1)
          .forEach(i -> array[i] = array[i + 1]);
    }
    elementsCount--;

    return deletedElement;
  }

  public void trimToSize() {
    array = Arrays.copyOf(array, elementsCount);
    capacity = elementsCount;
  }

  public Integer size() {
    return elementsCount;
  }

  @Override
  public String toString() {
    return Arrays.stream(array)
        .limit(elementsCount).map(Object::toString)
        .collect(Collectors.joining(", ", "[", "]"));
  }
}

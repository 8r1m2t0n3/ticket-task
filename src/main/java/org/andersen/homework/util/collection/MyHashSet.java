package org.andersen.homework.util.collection;

import java.util.Iterator;
import java.util.LinkedList;

public class MyHashSet<E> {

  private static final int DEFAULT_CAPACITY = 16;
  private static final float LOAD_FACTOR = 0.75f;

  private LinkedList<E>[] table;

  private int elementsCount;

  @SuppressWarnings("unchecked")
  public MyHashSet() {
    table = new LinkedList[DEFAULT_CAPACITY];
    elementsCount = 0;
  }

  public boolean add(E element) {
    int index = hash(element);

    if (elementsCount > LOAD_FACTOR * table.length) {
      resize();
    }

    if (table[index] == null) {
      table[index] = new LinkedList<>();
    }
    if (!table[index].contains(element)) {
      table[index].add(element);
      elementsCount++;
      return true;
    }
    return false;
  }

  public boolean remove(E element) {
    int index = hash(element);
    if (table[index] != null) {
      table[index].remove(element);
      elementsCount--;
      return true;
    }
    return false;
  }

  public boolean contains(E element) {
    int index = hash(element);
    return table[index] != null && table[index].contains(element);
  }

  private int hash(Object key) {
    return Math.abs(key.hashCode()) % table.length;
  }

  @SuppressWarnings("unchecked")
  private void resize() {
    LinkedList<E>[] tableCopy = table;
    table = new LinkedList[tableCopy.length + DEFAULT_CAPACITY];
    for (LinkedList<E> bucket : tableCopy) {
      if (bucket != null) {
        for (E key : bucket) {
          add(key);
        }
      }
    }
  }

  public int size() {
    return elementsCount;
  }

  public Iterator<E> iterator() {
    return new MyHashSetIterator();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("[");

    boolean isFirst = true;
    for (LinkedList<E> bucket : table) {
      if (bucket != null) {
        for (E element : bucket) {
          if (!isFirst) {
            builder.append(", ");
          }
          builder.append(element);
          isFirst = false;
        }
      }
    }

    builder.append("]");
    return builder.toString();
  }

  private class MyHashSetIterator implements Iterator<E> {

    private int bucketIndex = 0;
    private Iterator<E> bucketIterator = null;

    @Override
    public boolean hasNext() {
      while ((bucketIterator == null || !bucketIterator.hasNext()) && bucketIndex < table.length) {
        if (table[bucketIndex] != null) {
          bucketIterator = table[bucketIndex].iterator();
        }
        bucketIndex++;
      }
      return bucketIterator != null && bucketIterator.hasNext();
    }

    @Override
    public E next() {
      if (!hasNext()) {
        throw new java.util.NoSuchElementException();
      }
      return bucketIterator.next();
    }
  }
}

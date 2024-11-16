package org.andersen.homework.util.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;

public class MyHashSet<E> {

  private HashMap<E, Object> map;

  private final Object PRESENT = new Object();

  public MyHashSet() {
    map = new HashMap<>();
  }

  public MyHashSet(int initialCapacity) {
    map = new HashMap<>(initialCapacity);
  }

  public boolean add(E element) {
    return map.put(element, PRESENT) == null;
  }

  public boolean remove(Object o) {
    return map.remove(o) == PRESENT;
  }

  public boolean contains(Object o) {
    return map.containsKey(o);
  }

  public Iterator<E> iterator() {
    return map.keySet().iterator();
  }

  public int size() {
    return map.size();
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public String toString() {
    return map.keySet().stream()
        .map(Object::toString)
        .collect(Collectors.joining(", ", "[", "]"));
  }
}

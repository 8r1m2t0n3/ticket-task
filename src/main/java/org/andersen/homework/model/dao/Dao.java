package org.andersen.homework.model.dao;

import java.util.List;

public interface Dao<T, K> {
  T save(T t);
  void update(K id, T t);
  void delete(K id);
  T get(K id);
  List<T> getAll();
}

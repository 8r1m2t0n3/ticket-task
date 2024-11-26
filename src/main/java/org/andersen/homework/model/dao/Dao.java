package org.andersen.homework.model.dao;

import java.util.List;

public interface Dao<T, K> {
  T save(T t);
  void update(K id, T t);
  void remove(K id);
  T findById(K id);
  List<T> getAll();
}

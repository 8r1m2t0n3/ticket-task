package org.andersen.homework.model.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractHibernateDao<T, K> {

  @Autowired
  protected SessionFactory sessionFactory;

  private Class<T> clazz;

  public final void setClazz(final Class<T> clazz) {
    this.clazz = Preconditions.checkNotNull(clazz, "clazz");
  }

  public T save(T t) {
    return getCurrentSession().merge(t);
  }

  public void update(T t) {
    Preconditions.checkNotNull(t, clazz.getName());
    getCurrentSession().merge(t);
  }

  public void delete(T t) {
    Preconditions.checkNotNull(t, clazz.getName());
    getCurrentSession().remove(t);
  }

  public void deleteById(K id) {
    final T t = findById(id);
    Preconditions.checkNotNull(t, clazz.getName());
    delete(t);
  }

  public T findById(K id) {
    return getCurrentSession().get(clazz, id);
  }

  public List<T> getAll() {
    return getCurrentSession().createQuery("FROM " + clazz.getName(), clazz).list();
  }

  protected Session getCurrentSession() {
    return sessionFactory.getCurrentSession();
  }
}

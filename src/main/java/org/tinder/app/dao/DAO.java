package org.tinder.app.dao;

import java.util.List;
public interface DAO<A> {
    void add(A a);
    void remove(int id);
    A get(int id);
    List<A> getAll();
}

package dao;

import java.util.List;

public interface DAO<T> {
    void add(T t);
    void remove(int id);
    List<T> getAll();
    T get(int id);
}

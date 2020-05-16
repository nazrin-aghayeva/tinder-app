package dao;

import java.util.List;

<<<<<<< HEAD
public interface DAO <T>{
    void add(T item);
    void remove(int id);
    T get (int id);
    List<T> getAll();
=======
public interface DAO<T> {
    void add(T t);
    void remove(int id);
    List<T> getAll();
    T get(int id);
>>>>>>> b3c1cc5bdd2c710f63c70f70c55dc4a7e4a9eb03
}

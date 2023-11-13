package org.example.platzi.repository;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();
    T getById(Integer id);
    void save(T type);
    void delete(Integer id);
}

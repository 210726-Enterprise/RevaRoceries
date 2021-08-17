package com.revature.persistence;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    public Optional<List<T>> findAll();
    public Optional<T> findById(int id);
    public int insert (T t);
    public boolean update(T t);
    public boolean delete(int id);
}

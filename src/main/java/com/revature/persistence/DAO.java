package com.revature.persistence;

import java.util.List;

public interface DAO<T> {
    public List<T> findAll();
    public T findById(int id);
    public int insert (T t);
    public boolean update(T t);
    public boolean delete(int id);
}

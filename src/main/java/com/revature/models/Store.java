package com.revature.models;

import java.util.List;
import java.util.Map;

public class Store {

    private int storeId;

    private User owner;
    private List<User> employees;
    private Map<Product, Integer> inventory;

    public Store() {
    }

    public Store(User owner, List<User> employees, Map<Product, Integer> inventory) {
        this.owner = owner;
        this.employees = employees;
        this.inventory = inventory;
    }

    public Store(int storeId, User owner, List<User> employees, Map<Product, Integer> inventory) {
        this.storeId = storeId;
        this.owner = owner;
        this.employees = employees;
        this.inventory = inventory;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }

    public Map<Product, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<Product, Integer> inventory) {
        this.inventory = inventory;
    }


}

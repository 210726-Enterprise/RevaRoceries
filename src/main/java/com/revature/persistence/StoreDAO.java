package com.revature.persistence;

import com.revature.models.Store;
import com.revature.util.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StoreDAO implements DAO<Store>{
    private static final Logger logger = LoggerFactory.getLogger(StoreDAO.class);

    @Override
    public Optional<List<Store>> findAll() {
        List<Store> allStores = new ArrayList<>();

        try(Connection conn = ConnectionUtil.getConnection()){

        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Store> findById(int id) {
        return Optional.empty();
    }

    @Override
    public int insert(Store store) {
        return 0;
    }

    @Override
    public boolean update(Store store) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}

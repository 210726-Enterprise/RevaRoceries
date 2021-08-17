package com.revature.persistence;


import com.revature.models.Product;
import com.revature.util.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO implements DAO<Product>{
    private static final Logger logger = LoggerFactory.getLogger(ProductDAO.class);

    @Override
    public Optional<List<Product>> findAll() {
        List<Product> allProducts = new ArrayList<>();
        try(Connection conn = ConnectionUtil.getConnection()){

            String sql = "SELECT * from products";
            assert conn != null;
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                allProducts.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name")
                ));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }

        return Optional.of(allProducts);
    }

    @Override
    public Optional<Product> findById(int id) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * from products where product_id = ?";
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return Optional.of(new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name")
                ));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public int insert(Product product) {

        try(Connection conn = ConnectionUtil.getConnection()) {
            PreparedStatement stmt;
            String sql;
            assert conn != null;
            if(product.getProductId() != 0){
                sql = "INSERT into products (product_id, product_name) values (?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, product.getProductId());
                stmt.setString(2, product.getProductName());
            } else{
                sql = "INSERT into products (product_name) values (?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, product.getProductName());
            }
            return stmt.executeUpdate();
        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }

        return 0;
    }

    @Override
    public boolean update(Product product) {
        int success = 0;

        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE products set product_name = ?" +
                    "where product_id = ?";
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, product.getProductName());
            stmt.setInt(2, product.getProductId());

            success = stmt.executeUpdate();

        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }

        return success == 1;
    }

    @Override
    public boolean delete(int id) {
        int success = 0;

        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "DELETE from products where product_id = " + id;
            assert conn != null;
            Statement stmt = conn.createStatement();
            success = stmt.executeUpdate(sql);

        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }
        return success == 1;
    }
}

package com.revature.persistence;

import com.revature.models.AccountType;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * JDBC API:
 *
 * DriverManager class
 * 	- Some static methods, such as getConnection that we use to connect to a DB
 * 	- Used to obtain a Connection
 *
 * Connection Interface
 * 	- Represents a Connection to our DB
 * 	- Has methods to obtain Statements
 *
 * Statement Interface
 * 	- Represents a SQL statement that will be performed against the DB
 * 	- There are sub-interfaces for specific use-cases
 * 	- PreparedStatement Interface
 * 		- CallableStatement Interface
 * 	- Have methods to obtain ResultSets
 *
 * ResultSet Interface
 * 	- Represents data obtained from the DB
 * 	- Follows an "Iterator" structure
 * 		- Is pointing to individual rows
 * 		- Invoke the .next() method to step forward
 * 		- Starts at the position BEFORE the first row
 * 	- Has methods to obtain data from individual columns for that row
 * 		- getInt
 * 		- getString
 * 		- etc
 */
public class UserDAO implements DAO<User>{
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);


    @Override
    public Optional<List<User>> findAll() {
        List<User> allUsers = new ArrayList<>();

        // Try with resources
        try(Connection conn = ConnectionUtil.getConnection()){

            String sql = "SELECT * from users";
            assert conn != null;
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                allUsers.add(new User(
                        rs.getInt("user_id"),
                        AccountType.values()[rs.getInt("account_type_id")], // this converts from the ordinal to the type
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }

        return Optional.of(allUsers);
    }

    @Override
    public Optional<User> findById(int id) {

        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * from users where user_id = ?";
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return Optional.of(new User(
                        rs.getInt("user_id"),
                        AccountType.values()[rs.getInt("account_type_id")], // this converts from the ordinal to the type
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public int insert(User user) {

        try(Connection conn = ConnectionUtil.getConnection()){
            PreparedStatement stmt;
            String sql;
            assert conn != null;
            if(user.getUserId() != 0){
                sql = "INSERT into users (user_id, account_type_id, first_name, last_name, username, password) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, user.getUserId());
                stmt.setInt(2, user.getAccountType().ordinal());
                stmt.setString(3, user.getFirstName());
                stmt.setString(4, user.getLastName());
                stmt.setString(5, user.getUsername());
                stmt.setString(6, user.getPassword());

            } else{
                sql = "INSERT into users (account_type_id, first_name, last_name, username, password) " +
                        "VALUES (?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, user.getAccountType().ordinal());
                stmt.setString(2, user.getFirstName());
                stmt.setString(3, user.getLastName());
                stmt.setString(4, user.getUsername());
                stmt.setString(5, user.getPassword());
            }
            return stmt.executeUpdate();

        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }

        return 0;
    }

    @Override
    public boolean update(User user) {
        int success = 0;

        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE users set account_type_id = ?, first_name = ?," +
                    " last_name = ?, username = ?, password = ? " +
                    "where user_id = ?";
            assert conn != null;
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, user.getAccountType().ordinal());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getUsername());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, user.getUserId());
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
            String sql = "DELETE from users where user_id = " + id;
            assert conn != null;
            Statement stmt = conn.createStatement();
            success = stmt.executeUpdate(sql);

        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }
        return success == 1;
    }
}

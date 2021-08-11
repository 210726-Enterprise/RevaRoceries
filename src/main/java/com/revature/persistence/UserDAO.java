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

            String sql = "SELECT * from \"Users\"";
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                allUsers.add(new User(
                        rs.getInt("id"),
                        AccountType.values()[rs.getInt("accountType")], // this converts from the ordinal to the type
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException throwables) {
            logger.warn(throwables.getMessage());
        }

        return Optional.of(allUsers);
    }

    @Override
    public User findById(int id) {

        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "Select * from \"Users\" where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return new User(
                        rs.getInt("id"),
                        AccountType.values()[rs.getInt("accountType")], // this converts from the ordinal to the type
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException throwables) {
            logger.warn(throwables.getMessage(),throwables);
        }

        return null;
    }

    @Override
    public int insert(User user) {
        return 0;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}

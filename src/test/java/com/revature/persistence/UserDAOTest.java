package com.revature.persistence;

import com.revature.models.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UserDAOTest {

    private final UserDAO dao = new UserDAO();

    @Test
    public void findAllUsersTest(){
        List<User> users = dao.findAll();
        Assert.assertNotEquals(0, users.size());
    }

    @Test
    public void findUserByIdTest(){
        User user = dao.findById(1);
        Assert.assertEquals("Ted", user.getFirstName());
        Assert.assertEquals("Lasso", user.getLastName());
    }

    @Test
    public void createNewUserTest(){
        User user = new User();
        user.setFirstName("Brandon");

        Assert.assertNotEquals(0,dao.insert(user));
    }
}

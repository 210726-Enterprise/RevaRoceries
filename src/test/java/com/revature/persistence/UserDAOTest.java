package com.revature.persistence;

import com.revature.models.AccountType;
import com.revature.models.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDAOTest {

    private UserDAO dao;
    private User user;
    private final int USER_ID = 1;
    private final String FIRST_NAME = "Test";
    private final String LAST_NAME = "User";
    private final String USERNAME = "test_un";
    private final String PASSWORD = "test_pw";


    @BeforeEach
    void setUp() {
        dao = new UserDAO();

        user = new User(
                USER_ID,
                AccountType.CUSTOMER,
                FIRST_NAME,
                LAST_NAME,
                USERNAME,
                PASSWORD
        );
    }

    @Test
    @Order(1)
    void insert() {
        assertEquals(1, dao.insert(user));
    }

    @Test
    @Order(2)
    void findAll() {
        assertNotEquals(0, dao.findAll().orElseThrow(RuntimeException::new).size());
    }

    @Test
    @Order(3)
    void findById() {
        User user = dao.findById(USER_ID).orElseThrow(RuntimeException::new);
        assertEquals(USER_ID, user.getUserId());
        assertEquals(AccountType.CUSTOMER, user.getAccountType());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());
    }



    @Test
    @Order(4)
    void update() {
        String updated = "new value";
        user.setUsername(updated);
        assertTrue(dao.update(user));
    }

    @Test
    @Order(5)
    void delete() {
        assertTrue(dao.delete(USER_ID));
    }
}
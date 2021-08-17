package com.revature.persistence;

import com.revature.models.Product;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDAOTest {

    private ProductDAO dao;
    private Product product;
    private final int PRODUCT_ID = 1;
    private final String PRODUCT_NAME = "Noodle Soup";

    @BeforeEach
    void setUp() {
        dao = new ProductDAO();
        product = new Product(
                PRODUCT_ID,
                PRODUCT_NAME
        );
    }

    @Test
    @Order(1)
    void insert() {
        assertEquals(1, dao.insert(product));
    }

    @Test
    @Order(2)
    void findAll() {
        assertNotEquals(0, dao.findAll().orElseThrow(RuntimeException::new).size());
    }

    @Test
    @Order(3)
    void findById() {
        Product product = dao.findById(PRODUCT_ID).orElseThrow(RuntimeException::new);
        assertEquals(PRODUCT_ID, product.getProductId());
        assertEquals(PRODUCT_NAME, product.getProductName());
    }

    @Test
    @Order(4)
    void update() {
        String updated = "new value";
        product.setProductName(updated);
        assertTrue(dao.update(product));
    }

    @Test
    @Order(5)
    void delete() {
        assertTrue(dao.delete(PRODUCT_ID));
    }
}
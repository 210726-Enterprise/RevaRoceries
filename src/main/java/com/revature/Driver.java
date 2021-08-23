package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.persistence.ProductDAO;
import com.revature.persistence.UserDAO;
import com.revature.service.ProductService;
import com.revature.service.UserService;
import com.revature.servlet.ProductServlet;
import com.revature.servlet.UserServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Driver implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserDAO userDAO = new UserDAO();
        ProductDAO productDAO = new ProductDAO();

        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

        UserService userService = new UserService(userDAO, objectMapper);
        ProductService productService = new ProductService(productDAO, objectMapper);

        ServletContext context = sce.getServletContext();
        context.addServlet("User Servlet", new UserServlet(userService)).addMapping("/users");
        context.addServlet("Product Servlet", new ProductServlet(productService)).addMapping("/products");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}

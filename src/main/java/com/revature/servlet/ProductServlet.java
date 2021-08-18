package com.revature.servlet;

import com.revature.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/products")
public class ProductServlet extends HttpServlet {


    ProductService service;

    public ProductServlet() {
        this.service = new ProductService();
    }

    /*
            doGet needs to query the service for all of the products in the database.
         */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.getAllProducts(req, resp);
    }

    /*
        doPost needs to send the request and response to the service to parse through the request,
        convert the json to an object, then persist the object and update the response.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.insertProduct(req, resp);
    }

    /*
        doUpdate needs to send the request containing a product to update along with the response to the service
        to parse the JSON and send the updated product to the DAO. The service will then take the product back from
        the DAO and send it through the response.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.updateProduct(req, resp);
    }

    /*
        doDelete need the request and response to the service so the service can extract the id of the product
        it needs to delete.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.deleteProduct(req, resp);
    }
}

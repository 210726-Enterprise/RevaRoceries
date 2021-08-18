package com.revature.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Product;
import com.revature.persistence.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);


    private ProductDAO dao;
    private ObjectMapper mapper;

    public ProductService(){
        dao = new ProductDAO();
        mapper = new ObjectMapper();
    }

    public void getProducts(HttpServletRequest req, HttpServletResponse res){
        if(req.getParameter("productId") != null) {
            if(req.getParameter("productId").equals("")) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            try {
                String json = mapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(
                                getProduct(Integer.parseInt(req.getParameter("productId"))));

                if(json.equals("null")) {
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                res.getOutputStream().print(json);

            } catch (IOException e) {
                logger.warn(e.getMessage(), e);
            }
        } else {

            try {
                String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getAllProducts());
                res.getOutputStream().print(json);

            } catch (IOException e) {
                logger.warn(e.getMessage(), e);
            }
        }
    }

    public void insertProduct(HttpServletRequest req, HttpServletResponse resp) {
        try {
//            Map<String, String[]> map = req.getParameterMap();
//            Product product = mapper.convertValue(map, Product.class);


            StringBuilder builder = new StringBuilder();
            req.getReader().lines()
            .collect(Collectors.toList())
            .forEach(builder::append);

            Product product = mapper.readValue(builder.toString(), Product.class);
            int result = insert(product);

            if(result != 0){
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else{

                // TODO: refactor with exception propagation to set better status codes
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.warn(e.getMessage());
        }
    }

    public void updateProduct(HttpServletRequest req, HttpServletResponse resp) {
        StringBuilder builder = new StringBuilder();
        try {

            req.getReader().lines()
                    .collect(Collectors.toList())
                    .forEach(builder::append);

            Product product = mapper.readValue(builder.toString(), Product.class);

            if(product.getProductId() != 0){
                boolean result = update(product);

                if(result){
                    resp.setStatus(HttpServletResponse.SC_OK);

                    String JSON = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(product);
                    resp.getWriter().print(JSON);
                }

            } else{
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteProduct(HttpServletRequest req, HttpServletResponse resp) {
        boolean result = delete(Integer.parseInt(req.getParameter("productId")));

        if(result){
            resp.setStatus(HttpServletResponse.SC_OK);
        } else{
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    private Product getProduct(int id) {
        Optional<Product> result = dao.findById(id);
        return result.orElse(null);
    }

    private List<Product> getAllProducts(){
        Optional<List<Product>> result = dao.findAll();
        return result.orElseGet(ArrayList::new);

//                equivalent to the above
//         if(result.isPresent()){    we check if the optional is not empty
//            return result.get();    get the value out and return it
//        }
//        return new ArrayList<>();   or return an empty
    }

    private int insert(Product product){
        return dao.insert(product);
    }

    private boolean update(Product product){
        return dao.update(product);
    }

    private boolean delete(int id){
        return dao.delete(id);
    }





}

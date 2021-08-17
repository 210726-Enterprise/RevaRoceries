package com.revature.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.persistence.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    private UserDAO dao;
    private ObjectMapper mapper;

    public UserService(){
        dao = new UserDAO();
        mapper = new ObjectMapper();
    }

    public void getAllUsers(HttpServletRequest req, HttpServletResponse res){
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getUsers());
            res.getOutputStream().print(json);

        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
    }

    public void insertUser(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<String, String[]> map = req.getParameterMap();
            User user = mapper.convertValue(map, User.class);


//            StringBuilder builder = new StringBuilder();
//            req.getReader().lines()
//            .collect(Collectors.toList())
//            .forEach(builder::append);
//
//            User user = mapper.readValue(builder.toString(), User.class);
            int result = insert(user);

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

    private List<User> getUsers(){
        Optional<List<User>> result = dao.findAll();
        return result.orElseGet(ArrayList::new);

//                equivalent to the above
//         if(result.isPresent()){    we check if the optional is not empty
//            return result.get();    get the value out and return it
//        }
//        return new ArrayList<>();   or return an empty
    }

    private int insert(User user){
        return dao.insert(user);
    }


}

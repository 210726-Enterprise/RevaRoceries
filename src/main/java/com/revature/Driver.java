package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;

import java.io.IOException;

public class Driver {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        User user = new User();
        user.setUsername("jackson");
        user.setLastName("last_name");


        // Object to json
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user));

        String json = "{\n" +
                "  \"userId\" : 0,\n" +
                "  \"accountType\" : \"CUSTOMER\",\n" +
                "  \"firstName\" : null,\n" +
                "  \"lastName\" : \"last_name\",\n" +
                "  \"username\" : \"jackson\",\n" +
                "  \"password\" : null\n" +
                "}";

        // json to object
        User user2 = mapper.readValue(json, User.class);

        System.out.println(user2);

    }
}

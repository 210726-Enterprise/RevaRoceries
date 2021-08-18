package com.revature.servlet;

import com.revature.models.User;
import com.revature.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/users")
public class UserServlet extends HttpServlet {


    UserService service;

    public UserServlet() {
        this.service = new UserService();
    }

    /*
            doGet needs to query the service for all of the users in the database.
         */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.getAllUsers(req, resp);
    }

    /*
        doPost needs to send the request and response to the service to parse through the request,
        convert the json to an object, then persist the object and update the response.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.insertUser(req, resp);
    }

    /*
        doUpdate needs to send the request containing a user to update along with the response to the service
        to parse the JSON and send the updated user to the DAO. The service will then take the user back from
        the DAO and send it through the response.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.updateUser(req, resp);
    }

    /*
        doDelete need the request and response to the service so the service can extract the id of the user
        it needs to delete.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.deleteUser(req, resp);
    }
}

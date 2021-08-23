package com.revature.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.models.User;
import com.revature.persistence.UserDAO;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    UserService service;
    UserDAO daoMock;
    ObjectMapper mapperMock;
    ObjectWriter writerMock;
    HttpServletRequest requestMock;
    HttpServletResponse responseMock;
    List<User> usersList;
    ServletOutputStream outputStreamMock;
    String json;

    @BeforeEach
    void setUp() {
        daoMock = Mockito.mock(UserDAO.class);
        mapperMock = Mockito.mock(ObjectMapper.class);
        writerMock = Mockito.mock(ObjectWriter.class);
        requestMock = Mockito.mock(HttpServletRequest.class);
        responseMock = Mockito.mock(HttpServletResponse.class);
        outputStreamMock = Mockito.mock(ServletOutputStream.class);

        service = new UserService(daoMock, mapperMock);

        // Empty the list before each
        usersList = new ArrayList<>();
        json = "test json";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllUsers() throws IOException {
        usersList.add(new User());
        when(daoMock.findAll()).thenReturn(Optional.of(usersList));
        when(mapperMock.writerWithDefaultPrettyPrinter()).thenReturn(writerMock);
        when(mapperMock.writerWithDefaultPrettyPrinter().writeValueAsString(usersList)).thenReturn(json);
        when(responseMock.getOutputStream()).thenReturn(outputStreamMock);

        service.getAllUsers(requestMock, responseMock);

        // verify acts as an assertion confirming that the response code was added successfully
        verify(responseMock).setStatus(HttpServletResponse.SC_OK);
        verify(outputStreamMock).print(json);
    }

//    @Test
//    void getAllUsersThrowsException() throws IOException {
//
//
//        Assertions.assertThrows(JsonProcessingException.class, () ->
//        {
//            usersList.add(any(User.class));
//            when(daoMock.findAll()).thenReturn(Optional.of(usersList));
//            when(mapperMock.writerWithDefaultPrettyPrinter()).thenReturn(writerMock);
//            when(mapperMock.writerWithDefaultPrettyPrinter().writeValueAsString(usersList))
//                    .thenThrow(JsonProcessingException.class);
//            when(responseMock.getOutputStream()).thenReturn(outputStreamMock);
//            service.getAllUsers(requestMock, responseMock);
//        });
//
//    }

    @Test
    void insertUser() {

    }

    @Test
    void updateUser() {

    }

    @Test
    void deleteUser() {

    }
}